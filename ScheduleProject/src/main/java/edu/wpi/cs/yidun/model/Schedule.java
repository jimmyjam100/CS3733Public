package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;


public class Schedule {
	LocalTime startDate;
	LocalTime endDate;
	ArrayList<Week> weeks;
	String name;
	String id;
	String password;
	int minPerTimeslot;
	
	public Schedule(LocalTime startDate, LocalTime endDate, String name, String id, String password, int minPerTimeslot) {
		weeks = new ArrayList<Week>();
		this.startDate= startDate;
		this.endDate= endDate;
		this.name = name;
		this.id= id;
		this.password= password;
		this.minPerTimeslot= minPerTimeslot;
	}
	
	boolean closeWeekday(int weekday){
		boolean allClosed = true;
		for (Week w : weeks) {
			if (!w.closeDay(weekday)) {
				allClosed = false;
			}
		}
		return allClosed;
	}
	
	boolean closeTime(LocalTime t){
		boolean allClosed = true;
		for (Week w : weeks) {
			if (!w.closeTime(t)) {
				allClosed = false;
			}
		}
		return allClosed;
	}

	//setters
	void setStartDate(LocalTime t) {
		startDate = t;
	}
	
	void setEndDate(LocalTime t) {
		endDate = t;
	}
	
	void setName(String s) {
		name = s;
	}
	
	void setId(String s) {
		id = s;
	}
	
	void setPassword(String s) {
		password = s;
	}
	
	void setMinPerTimeslot(int i) {
		minPerTimeslot = i;
	}
	
	//getters
	LocalTime getStartDate() {
		return startDate;
	}
	
	LocalTime setEndDate() {
		return endDate;
	}
	
	ArrayList<Week> getWeeks() {
		return weeks;
	}
	
	String getName() {
		return name;
	}
	
	String getId() {
		return id;
	}
	
	String getPassword() {
		return password;
	}
	
	int getMinPerTimeslot() {
		return minPerTimeslot;
	}
}
