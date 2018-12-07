package edu.wpi.cs.yildun.demo;

public class CloseTimeslotRequest {
	int id;
	int schedId;
	String password;
	
	public CloseTimeslotRequest(int id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public String toString() {
		return "CloseTimeslotRequest(" + id + ")";
	}

}
