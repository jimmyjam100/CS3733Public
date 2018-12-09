package edu.wpi.cs.yildun.demo;

public class CancelMeetingRequest {
	int id;
	int schedId;
	String password;
	public CancelMeetingRequest(int id, int schedId, String password){
		this.id = id;
		this.schedId = schedId;
		this.password = password;
	}
	
	public String toString() {
		return "CancelMeetingRequest(" + id + ")";
	}

}
