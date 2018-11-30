package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Timeslot {

	ArrayList<Meeting> meeting;
	boolean open;
	LocalTime time;
	String id;
	
	public Timeslot(boolean open, LocalTime time) {
		this.open = open;
		this.time = time;
	}
	
	boolean createMeeting(Meeting m){
		if (!open || meeting.size() > 0) return false;
		meeting.add(m);
		return true;
	}
	
	boolean cancelMeeting(){
		if (!open || meeting.size() != 1) return false;
		meeting.remove(0);
		return true;
	}
	
	boolean open() {
		if (open) return false;
		open = true;
		return true;
	}
	
	boolean close() {
		if (!open) return false;
		open = false;
		return true;
	}
	
	//setters
	void setTime(LocalTime t) {
		time = t;
	}
	
	void setId(String s) {
		id = s;
	}
	
	//getters
	LocalTime getTime() {
		return time;
	}
	
	String getId() {
		return id;
	}

	ArrayList<Meeting> getMeeting(){
		return meeting;
	}
	
	boolean isOpen(){
		return open;
	}
}
