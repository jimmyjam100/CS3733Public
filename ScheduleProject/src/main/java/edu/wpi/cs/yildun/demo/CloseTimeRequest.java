package edu.wpi.cs.yildun.demo;

public class CloseTimeRequest {
	int id;
	int schedId;
	String password;
	
	public CloseTimeRequest(int id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public String toString() {
		return "CloseTimeslotRequest(" + id + ")";
	}

}
