package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Day {


	ArrayList<Timeslot> timeslots;
	int weekday;
	Date date;
	String id;
	
	public Day(int weekday, Date date, String id) {
		timeslots = new ArrayList<Timeslot>();
		this.weekday = weekday;
		this.date = date;
		this.id = id;
	}
	
	public boolean closeTime(LocalTime t){
		for (Timeslot slot : timeslots) {
			if (slot.time == t) {
				return slot.close();
			}
		}
		return false;
	}
	
	public boolean closeDay(){
		boolean allClosed = true;
		for (Timeslot slot : timeslots) {
			if (!slot.close()) {
				allClosed = false;
			}
		}
		return allClosed;
	}

	//setters
	public void setWeekday(int i) {
		weekday = i;
	}
	
	public void setDate(Date d) {
		date = d;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	//getters
	
	public int getWeekday() {
		return weekday;
	}
	
	public Date getDate() {
		return date;
	}
	
	public ArrayList<Timeslot> getTimeslots() {
		return timeslots;
	}
	public String getId() {
		return id;
	}
}
