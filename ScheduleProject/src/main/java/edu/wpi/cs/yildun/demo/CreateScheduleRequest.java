package edu.wpi.cs.yildun.demo;

import java.time.LocalTime;
import java.util.Date;

public class CreateScheduleRequest {
	String name;
	Date startDate;
	Date endDate;
	LocalTime startTime;
	LocalTime endTime;
	int timeSlotLen;
	
	public CreateScheduleRequest(String n, Date sD, Date eD, LocalTime sT, LocalTime eT, int timeSlotL) {
		name = n;
		startDate = sD;
		endDate = eD;
		startTime = sT;
		endTime = eT;
		timeSlotLen = timeSlotL;
	}
	
	public String toString() {
		return "CreateSchedule("+startDate.toString()+", " + endDate.toString() + ", " + ", " + startTime.toString() + ", " + endTime.toString() + ", " + timeSlotLen + ")";
	}

}
