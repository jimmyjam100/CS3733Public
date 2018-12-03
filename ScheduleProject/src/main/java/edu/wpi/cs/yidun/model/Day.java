package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Day {


	ArrayList<Timeslot> timeslots;
	int weekday;
	Date date;
	
	public Day(int weekday, Date date) {
		timeslots = new ArrayList<Timeslot>();
		this.weekday = weekday;
		this.date = date;
	}
	
	boolean closeTime(LocalTime t){
		for (Timeslot slot : timeslots) {
			if (slot.time == t) {
				return slot.close();
			}
		}
		return false;
	}
	
	boolean closeDay(){
		boolean allClosed = true;
		for (Timeslot slot : timeslots) {
			if (!slot.close()) {
				allClosed = false;
			}
		}
		return allClosed;
	}

	//setters
	void setWeekday(int i) {
		weekday = i;
	}
	
	void setDate(Date d) {
		date = d;
	}
	
	//getters
	
	int getWeekday() {
		return weekday;
	}
	
	Date getDate() {
		return date;
	}
	
	ArrayList<Timeslot> getTimeslots() {
		return timeslots;
	}
}
