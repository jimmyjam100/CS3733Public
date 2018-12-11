package edu.wpi.cs.yildun.demo;

public class GetNewSchedulesRequest {
	int hour;
	
	public GetNewSchedulesRequest(int hour) {
		this.hour = hour;
	}
	
	public String toString() {
		return "GetNewScheduleRequest(" + this.hour + ")";
	}

}
