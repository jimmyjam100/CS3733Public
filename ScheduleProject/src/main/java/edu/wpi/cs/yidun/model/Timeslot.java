package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Timeslot {

	String user = "";
	String password = "";
	boolean open;
	LocalTime time;
	int id = -1;
	
	public Timeslot(boolean open, LocalTime time) {
		this.open = open;
		this.time = time;
	}
	
	public boolean cancelMeeting(){
		if (open) {
			return false;
		}
		else {
			open = true;
			user = "";
			password = "";
			return true;
		}
	}
	
	public boolean open() {
		if (open) return false;
		open = true;
		return true;
	}
	
	public boolean close() {
		if (!open) return false;
		open = false;
		return true;
	}
	
	//setters
	public void setTime(LocalTime t) {
		time = t;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void makeMeeting(String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	//getters
	public LocalTime getTime() {
		return time;
	}
	
	public int getId() {
		return id;
	}

	public String getUser(){
		return user;
	}
	public String getPassword() {
		return password;
	}
	
	public boolean isOpen(){
		return open;
	}
}
