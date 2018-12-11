package edu.wpi.cs.yildun.demo;

import java.util.Date;

public class ExtendStartRequest {
	int id;
	Date newDate;
	String password;
	public ExtendStartRequest(int id, Date newDate, String password) {
		this.id = id;
		this.newDate = newDate;
		this.password = password;
	}
	
	public String toString() {
		return "ExtendStartRequest("+ id +", " + newDate + ")";
	}

}
