package edu.wpi.cs.yildun.demo;

public class CloseDayRequest {
	String date;
	int schedId;
	String password;
	
	public CloseDayRequest(String date, int schedId, String password) {
		this.date = date;
		this.schedId = schedId;
		this.password = password;
	}
	
	public String toString() {
		return "CloseTimeslotRequest(" + date + ")";
	}

}
