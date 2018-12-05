package edu.wpi.cs.yildun.demo;

public class CancelMeetingRequest {
	int id;
	public CancelMeetingRequest(int id){
		this.id = id;
	}
	
	public String toString() {
		return "CancelMeetingRequest(" + id + ")";
	}

}
