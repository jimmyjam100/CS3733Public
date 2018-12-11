package edu.wpi.cs.yildun.demo;

import java.util.Date;

public class ExtendEndRequest {
	int id;
	Date newDate;
	String password;
	public ExtendEndRequest(int id, Date newDate, String password) {
		this.id = id;
		this.newDate = newDate;
		this.password = password;
	}
	
	public String toString() {
		return "ExtendStartRequest("+ id +", " + newDate + ")";
	}

}
