package edu.wpi.cs.yildun.demo;

public class DeleteScheduleRequest {
	int id;
	String password;
	
	public DeleteScheduleRequest(int id, String password) {
		this.id = id;
		this.password = password;
	}
	
	public String toString() {
		return "DeleteScheduleRequest(" + this.id + ")";
	}

}
