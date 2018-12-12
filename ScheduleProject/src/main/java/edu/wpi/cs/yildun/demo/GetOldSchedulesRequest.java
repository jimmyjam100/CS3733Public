package edu.wpi.cs.yildun.demo;

public class GetOldSchedulesRequest {
	int days;
	boolean delete;
	
	
	public GetOldSchedulesRequest(int days, boolean delete) {
		this.days = days;
		this.delete = delete;
	}
	
	public String toString() {
		return "GetNewScheduleRequest(" + this.days + ")";
	}

}
