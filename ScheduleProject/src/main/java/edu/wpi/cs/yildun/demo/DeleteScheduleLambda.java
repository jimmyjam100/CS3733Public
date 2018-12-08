package edu.wpi.cs.yildun.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

import edu.wpi.cs.yidun.db.ScheduleDAO;
import edu.wpi.cs.yidun.model.Week;

public class DeleteScheduleLambda implements RequestStreamHandler {
	
	void deleteSchedule(int id) throws Exception {
		ScheduleDAO dao = new ScheduleDAO();
		dao.deleteSchedule(id);
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

		DeleteScheduleResponse response = null;
		
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
				response = new DeleteScheduleResponse(200);  // OPTIONS needs a 200 response
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
			response = new DeleteScheduleResponse(422);  // unable to process input
	        responseJson.put("body", new Gson().toJson(response));
	        processed = true;
	        body = null;
		}

		if (!processed) {
			DeleteScheduleRequest req = new Gson().fromJson(body, DeleteScheduleRequest.class);
			DeleteScheduleResponse resp = new DeleteScheduleResponse(400);
			try {
				if (validate(req.id, req.password)) {
					deleteSchedule(req.id);
					resp = new DeleteScheduleResponse(200);
				}
				else {
					resp = new DeleteScheduleResponse(420);
				}
			} catch (Exception e) {
				resp = new DeleteScheduleResponse(400);
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
