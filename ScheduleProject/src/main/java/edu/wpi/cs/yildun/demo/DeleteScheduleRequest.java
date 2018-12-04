package edu.wpi.cs.yildun.demo;

public class DeleteScheduleRequest {
	String id;
	
	public DeleteScheduleRequest(String id) {
		this.id = id;
	}
	
	public String toString() {
		return "DeleteScheduleRequest(" + this.id + ")";
	}

}
