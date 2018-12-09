package edu.wpi.cs.yildun.demo;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

import edu.wpi.cs.yidun.model.Day;
import edu.wpi.cs.yidun.model.Schedule;
import edu.wpi.cs.yidun.model.Timeslot;
import edu.wpi.cs.yidun.model.Week;

import edu.wpi.cs.yidun.db.ScheduleDAO;

public class GetAllOpenTimeslotsLambda implements RequestStreamHandler {
	
	ArrayList<Timeslot> getTimeslots(int id) throws NumberFormatException, Exception {
		ScheduleDAO dao = new ScheduleDAO();
		Schedule sched = dao.getSchedule(id);
		ArrayList<Timeslot> timeslots = new ArrayList<Timeslot>();
		for(Week w : sched.getWeeks()) {
			for(Day d : w.getDays()) {
				for(Timeslot t : d.getTimeslots()) {
					if(t.isOpen()) {
						timeslots.add(t);
					}
				}
			}
		}

		return timeslots;
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

		GetAllOpenTimeslotsResponse response = null;
		
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
				response = new GetAllOpenTimeslotsResponse(null, 200);  // OPTIONS needs a 200 response
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
			response = new GetAllOpenTimeslotsResponse(null, 422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			GetAllOpenTimeslotsRequest req = new Gson().fromJson(body, GetAllOpenTimeslotsRequest.class);
			ArrayList<Timeslot> timeslots = null;
			GetAllOpenTimeslotsResponse resp;
			try {
				timeslots = getTimeslots(req.id);
				if (timeslots == null) {
					resp = new GetAllOpenTimeslotsResponse(null, 400);
				}
				else {
					resp = new GetAllOpenTimeslotsResponse(timeslots, 200);
				}
			} catch (java.text.ParseException e) {
				resp = new GetAllOpenTimeslotsResponse(null, 401);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				resp = new GetAllOpenTimeslotsResponse(null, 402);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				resp = new GetAllOpenTimeslotsResponse(null, 403);
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
