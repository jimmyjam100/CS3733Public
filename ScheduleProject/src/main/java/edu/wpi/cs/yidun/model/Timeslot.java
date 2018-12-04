package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Timeslot {

	Meeting meeting;
	boolean open;
	LocalTime time;
	String id;
	
	public Timeslot(boolean open, LocalTime time, String id) {
		this.open = open;
		this.time = time;
		this.meeting = null;
		this.id = id;
	}
	
	public boolean cancelMeeting(){
		if (this.meeting == null) {
			return false;
		}
		this.meeting = null;
		return true;
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
	
	public void setId(String s) {
		id = s;
	}
	
	public void setMeeting(Meeting m) {
		this.meeting = m;
	}
	
	//getters
	public LocalTime getTime() {
		return time;
	}
	
	public String getId() {
		return id;
	}

	public Meeting getMeeting(){
		return meeting;
	}
	
	public boolean isOpen(){
		return open;
	}
}
