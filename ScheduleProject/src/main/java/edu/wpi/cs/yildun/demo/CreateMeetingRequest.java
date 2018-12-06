package edu.wpi.cs.yildun.demo;

public class CreateMeetingRequest {
	int id;
	String user;
	public CreateMeetingRequest(int id, String user) {
		this.id = id;
		this.user = user;
	}
	
	public String toString() {
		return "CreateMeetingRequest(" + id + ", " + user + ")";
	}

}
