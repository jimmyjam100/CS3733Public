package edu.wpi.cs.yildun.demo;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import org.joda.time.Weeks;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.gson.Gson;

import edu.wpi.cs.yidun.db.ScheduleDAO;
import edu.wpi.cs.yidun.model.Day;
import edu.wpi.cs.yidun.model.Schedule;
import edu.wpi.cs.yidun.model.Timeslot;
import edu.wpi.cs.yidun.model.Week;




/**
 * Found gson JAR file from
 * https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar
 */
public class LambdaFunctionHandler implements RequestStreamHandler {

	public LambdaLogger logger = null;
	
	public static Date resetTime (Date d) {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(d);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	}
	
	public static int getWeeksBetween (Date a, Date b) {
	    if (b.before(a)) {
	        return -getWeeksBetween(b, a);
	    }
	    a = resetTime(a);
	    b = resetTime(b);

	    Calendar cal = new GregorianCalendar();
	    cal.setTime(a);
	    int weeks = 0;
	    while (cal.getTime().before(b)) {
	        // add another week
	        cal.add(Calendar.WEEK_OF_YEAR, 1);
	        weeks++;
	    }
	    return weeks;
	}
	
	String randId() {
		return "randId";
	}
	
	Schedule newSchedule(String n, Date sD, Date eD, LocalTime sT, LocalTime eT, int timeslotL) {
		Schedule temp = new Schedule(sD, eD, sT, eT, n, randId(), "password", timeslotL);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sD);
		for(int i = 0; cal.getTime().before(eD); i++) {
			Week tempWeek = new Week(i);
			for (int j = (cal.get(Calendar.DAY_OF_WEEK)) - 2; (cal.get(Calendar.DAY_OF_WEEK)) - 2 < 5 && cal.getTime().before(eD); j++) {
				Day tempDay = new Day(j, cal.getTime());
				
		        int startMin = sT.get(ChronoField.MINUTE_OF_DAY);
		        int endMin = eT.get(ChronoField.MINUTE_OF_DAY);
		        for (int k = 0; k < endMin - startMin; k+=timeslotL) {
		       		tempDay.getTimeslots().add(new Timeslot(true, (sT.plusMinutes(j))));
		       	}
				tempWeek.addDay(tempDay);
				cal.add(Calendar.DATE, 1);
			}
			while(cal.get(Calendar.DAY_OF_WEEK) - 2 != 0) {
				cal.add(Calendar.DATE, 1);
			}
			temp.getWeeks().add(tempWeek);
		}
		return temp;
	}
	
	void createSchedule(Schedule sched) throws Exception {
		ScheduleDAO dao = new ScheduleDAO();
		dao.addSchedule(sched);
	}
	
	boolean createSchedule(String n, Date sD, Date eD, LocalTime sT, LocalTime eT, int timeslotL) throws Exception {
		Schedule temp = newSchedule(n, sD, eD, sT, eT, timeslotL);
		
		/*if (logger != null) { logger.log("in createConstant"); }
		ScheduleDAO dao = new ScheduleDAO();
		
		// check if present
		//Schedule exist = dao.getSchedule(n);
		Schedule sched = new Schedule (sD);
		return dao.addSchedule(sched);*/
		return true;
	}
	

	// handle to our s3 storage
	private AmazonS3 s3 = AmazonS3ClientBuilder.standard()
			.withRegion("us-east-2").build();

	boolean useRDS = true;
	
	// not yet connected to RDS, so comment this out...

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	
	/** Load up S3 Bucket with given key and interpret contents as double. */
	double loadValueFromBucket(String arg) {
		if (logger != null) { logger.log("load from bucket:" + arg); }
		try {
			S3Object pi = s3.getObject("cs3733/constants", arg);
			if (pi == null) {
				return 0;
			} else {
				S3ObjectInputStream pis = pi.getObjectContent();
				Scanner sc = new Scanner(pis);
				String val = sc.nextLine();
				sc.close();
				try { pis.close(); } catch (IOException e) { }
				try {
					return Double.valueOf(val);
				} catch (NumberFormatException nfe) {
					return 0.0;
				}
			}
		} catch (SdkClientException sce) {
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		LambdaLogger logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestStreamHandler");

		JSONObject headerJson = new JSONObject();
		headerJson.put("Content-Type",  "application/json");  // not sure if needed anymore?
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");
	        
		JSONObject responseJson = new JSONObject();
		responseJson.put("headers", headerJson);

		CreateScheduleResponse response = null;
		
		// extract body from incoming HTTP POST request. If any error, then return 422 error
		String body;
		boolean processed = false;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONParser parser = new JSONParser();
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("event:" + event.toJSONString());
			
			String method = (String) event.get("httpMethod");
			if (method != null && method.equalsIgnoreCase("OPTIONS")) {
				logger.log("Options request");
				response = new CreateScheduleResponse(null, 200);  // OPTIONS needs a 200 response
		        responseJson.put("body", new Gson().toJson(response));
		        processed = true;
		        body = null;
			} else {
				body = (String)event.get("body");
				if (body == null) {
					body = event.toJSONString();  // this is only here to make testing easier
				}
			}
		} catch (ParseException pe) {
			logger.log(pe.toString());
			response = new CreateScheduleResponse(null, 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			CreateScheduleRequest req = new Gson().fromJson(body, CreateScheduleRequest.class);
			logger.log(req.toString());
			Schedule tempSched = null;
			try {
				tempSched = newSchedule(req.name, new SimpleDateFormat("yyyy-MM-dd").parse(req.startDate), new SimpleDateFormat("yyyy-MM-dd").parse(req.endDate), LocalTime.parse(req.startTime, DateTimeFormatter.ofPattern("HH:mm")), LocalTime.parse(req.endTime, DateTimeFormatter.ofPattern("HH:mm")), req.timeslotLen);
				createSchedule(tempSched);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CreateScheduleResponse resp;
			if(tempSched == null) {
				resp = new CreateScheduleResponse(null, 400);
			}
			else {
				resp = new CreateScheduleResponse(tempSched, 200);
			}
	        responseJson.put("body", new Gson().toJson(resp));  
		}
		
        logger.log("end result:" + responseJson.toJSONString());
        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
	}
}
