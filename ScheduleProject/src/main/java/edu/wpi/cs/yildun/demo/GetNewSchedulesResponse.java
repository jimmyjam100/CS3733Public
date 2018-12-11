package edu.wpi.cs.yildun.demo;

import java.util.ArrayList;

import edu.wpi.cs.yidun.model.Schedule;

public class GetNewSchedulesResponse {
	ArrayList<Schedule> scheds;
	int httpCode;
	
	public GetNewSchedulesResponse(ArrayList<Schedule> scheds, int code) {
		this.scheds = scheds;
		httpCode = code;
	}
	
	public String toString() {
		return "response(" + scheds + ", " + httpCode + ")";
	}
	

}
