package edu.wpi.cs.yildun.demo;

import edu.wpi.cs.yidun.model.Week;

public class GetWeekResponse {
	Week week;
	int httpCode;
	public GetWeekResponse(Week week, int code) {
		this.week = week;
		httpCode = code;
	}
	public String toString() {
		return "Response(" + this.week.toString() + ")";
	}

}
