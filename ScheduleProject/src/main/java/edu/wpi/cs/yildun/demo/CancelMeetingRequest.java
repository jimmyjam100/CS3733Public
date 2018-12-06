package edu.wpi.cs.yildun.demo;

public class CancelMeetingRequest {
	int id;
	String password;
	public CancelMeetingRequest(int id, String password){
		this.id = id;
		this.password = password;
	}
	
	public String toString() {
		return "CancelMeetingRequest(" + id + ")";
	}

}
