package edu.wpi.cs.yildun.demo;

import java.time.LocalTime;
import java.util.Date;

public class CreateScheduleRequest {
	String name;
	String startDate;
	String endDate;
	String startTime;
	String endTime;
	int timeslotLen;
	
	public CreateScheduleRequest(String name, String startDate, String endDate, String startTime, String endTime, int timeslotLen) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeslotLen = timeslotLen;
	}
	
	public String toString() {
		return "CreateSchedule(" + startDate + ", " + endDate + ", " + ", " + startTime + ", " + endTime + ", " + timeslotLen + ")";
	}

}
