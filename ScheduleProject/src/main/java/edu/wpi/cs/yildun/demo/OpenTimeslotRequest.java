package edu.wpi.cs.yildun.demo;

public class OpenTimeslotRequest {
	int id;
	int schedId;
	String password;
	
	public OpenTimeslotRequest(int id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public String toString() {
		return "CloseTimeslotRequest(" + id + ")";
	}

}
