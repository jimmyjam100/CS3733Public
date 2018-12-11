package edu.wpi.cs.yildun.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

import edu.wpi.cs.yidun.db.ScheduleDAO;
import edu.wpi.cs.yidun.model.Day;
import edu.wpi.cs.yidun.model.Schedule;
import edu.wpi.cs.yidun.model.Timeslot;
import edu.wpi.cs.yidun.model.Week;

public class ExtendEndLambda implements RequestStreamHandler {
	
	boolean extendEnd(int id, Date d) throws Exception {
		ScheduleDAO dao = new ScheduleDAO();
		Schedule sched = dao.getSchedule(id);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sched.getEndDate());
		if(!cal.before(d)) {
			return false;
		}
		ArrayList<Week> weeks = new ArrayList<Week>();
		for(int i = 0; cal.getTime().before(d) || cal.getTime().equals(d); i++) {
			Week tempWeek = new Week(i);
			for (int j = (cal.get(Calendar.DAY_OF_WEEK)) - 2; (cal.get(Calendar.DAY_OF_WEEK)) - 2 < 5 && (cal.get(Calendar.DAY_OF_WEEK)) - 2 > -1 && (cal.getTime().before(d) || cal.getTime().equals(d)); j++) {
				Day tempDay = new Day(j, cal.getTime());
				
		        int startMin = sched.getStartTime().get(ChronoField.MINUTE_OF_DAY);
		        int endMin = sched.getEndTime().get(ChronoField.MINUTE_OF_DAY);
		        for (int k = 0; k < endMin - startMin; k+=sched.getMinPerTimeslot()) {
		       		tempDay.getTimeslots().add(new Timeslot(true, (sched.getStartTime().plusMinutes(k))));
		       	}
				tempWeek.addDay(tempDay);
				cal.add(Calendar.DATE, 1);
			}
			while(cal.get(Calendar.DAY_OF_WEEK) - 2 != 0) {
				cal.add(Calendar.DATE, 1);
			}
			weeks.add(tempWeek);
		}
		for(Week w : weeks) {
			for (Day d1 : w.getDays()) {
				for (Timeslot t : d1.getTimeslots()) {
					dao.addTimeslot(id, t, new java.sql.Date(d1.getDate().getTime()));
				}
			}
		}
		sched.setEndDate(d);
		dao.updateSchedule(sched);
		return true;
	}
	
	boolean validate(int id, String password) throws Exception {
		ScheduleDAO dao = new ScheduleDAO();
		return dao.getSchedule(id).getPassword().equals(password);
	}

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

		ExtendEndResponse response = null;
		
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
				response = new ExtendEndResponse(200);  // OPTIONS needs a 200 response
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
			response = new ExtendEndResponse(422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			ExtendEndRequest req = new Gson().fromJson(body, ExtendEndRequest.class);
			ExtendEndResponse resp = new ExtendEndResponse(400);
			try {
				if (validate(req.id, req.password)) {
					if (extendEnd(req.id, req.newDate)) {
						resp = new ExtendEndResponse(200);
					}
					else {
						resp = new ExtendEndResponse(421);
					}
				}
				else {
					resp = new ExtendEndResponse(420);
				}
			} catch (Exception e) {
				resp = new ExtendEndResponse(400);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// compute proper response
	        responseJson.put("body", new Gson().toJson(resp));  
		}
		
        logger.log("end result:" + responseJson.toJSONString());
        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
	}

}
