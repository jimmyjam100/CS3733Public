package edu.wpi.cs.yildun.demo;

public class OpenTimeRequest {
	int id;
	int schedId;
	String password;
	
	public OpenTimeRequest(int id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public String toString() {
		return "CloseTimeslotRequest(" + id + ")";
	}

}
