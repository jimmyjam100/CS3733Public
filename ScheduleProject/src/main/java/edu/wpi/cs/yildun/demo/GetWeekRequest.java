package edu.wpi.cs.yildun.demo;

import java.util.Date;

public class GetWeekRequest {
	String schedId;
	Date date;
	public GetWeekRequest(String schedId, Date date) {
		this.schedId = schedId;
		this.date = date;
	}
	public String toString() {
		return "GetWeek(" + schedId + ", " + date.toString() + ")";
	}

}
