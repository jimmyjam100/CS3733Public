package edu.wpi.cs.yildun.demo;

import java.util.ArrayList;

import edu.wpi.cs.yidun.model.Schedule;

public class GetSchedulesResponse {
	ArrayList<Schedule> schedList;
	int httpCode;
	
	public GetSchedulesResponse(ArrayList<Schedule> schedList, int code) {
		this.schedList = schedList;
		this.httpCode = code;
	}

}
