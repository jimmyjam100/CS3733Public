package edu.wpi.cs.yildun.demo;

import java.util.Date;

public class GetWeekRequest {
	int schedId;
	String date;
	public GetWeekRequest(int schedId, String date) {
		this.schedId = schedId;
		this.date = date;
	}
	public String toString() {
		return "GetWeek(" + schedId + ", " + date.toString() + ")";
	}

}
