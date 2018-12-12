package edu.wpi.cs.yildun.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
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

public class CloseDayLambda implements RequestStreamHandler {
	
	void closeDay(int id, Date d) throws Exception{
		ScheduleDAO dao = new ScheduleDAO();
		Schedule s = dao.getSchedule(id);
		for(Week w : s.getWeeks()) {
			for(Day d2 : w.getDays()) {
				if (d2.getDate().equals(d2)) {
					for(Timeslot ts : d2.getTimeslots()) {
						if(!(ts.getUser().equals(""))) {
							ts.close();
							dao.updateTimeslot(ts);
						}
					}
				}
			}
		}
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

		CloseDayResponse response = null;
		
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
				response = new CloseDayResponse(200);  // OPTIONS needs a 200 response
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
			response = new CloseDayResponse(422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			CloseDayRequest req = new Gson().fromJson(body, CloseDayRequest.class);
			CloseDayResponse resp = new CloseDayResponse(400);
			try {
				if (validate(req.schedId, req.password)) {
					closeDay(req.schedId, new SimpleDateFormat("yyyy-MM-dd").parse(req.date));
					resp = new CloseDayResponse(200);
				}
				else {
					resp = new CloseDayResponse(420);
				}

			} catch (Exception e) {
				resp = new CloseDayResponse(400);
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
