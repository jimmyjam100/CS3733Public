package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Day {


	ArrayList<Timeslot> timeslots;
	int weekday;
	LocalTime date;
	
	public Day(int weekday, LocalTime date) {
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
	
	void setDate(LocalTime d) {
		date = d;
	}
	
	//getters
	
	int getWeekday() {
		return weekday;
	}
	
	LocalTime getDate() {
		return date;
	}
	
	ArrayList<Timeslot> getTimeslots() {
		return timeslots;
	}
}
