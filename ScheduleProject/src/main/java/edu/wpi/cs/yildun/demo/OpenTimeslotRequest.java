package edu.wpi.cs.yildun.demo;

public class OpenTimeslotRequest {
	int id;
	int schedId;
	String password;
	
	public OpenTimeslotRequest(int id, int schedId, String password) {
		this.id = id;
		this.schedId = schedId;
		this.password = password;
	}
	
	public String toString() {
		return "CloseTimeslotRequest(" + id + ")";
	}

}
