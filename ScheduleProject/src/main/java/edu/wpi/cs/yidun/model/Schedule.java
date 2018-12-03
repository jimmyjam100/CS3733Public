package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;


public class Schedule {
	LocalTime startTime;
	LocalTime endTime;
	Date startDate;
	Date endDate;
	ArrayList<Week> weeks;
	String name;
	String id;
	String password;
	int minPerTimeslot;
	
	public Schedule(Date startDate, Date endDate, LocalTime startTime, LocalTime endTime, String name, String id, String password, int minPerTimeslot) {
		weeks = new ArrayList<Week>();
		this.startTime= startTime;
		this.endTime= endTime;
		this.startDate = startDate;
		this.endDate = endDate;
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
	void setStartTime(LocalTime t) {
		startTime = t;
	}
	
	void setEndTime(LocalTime t) {
		endTime = t;
	}
	
	void setStartDate(Date d) {
		startDate = d;
	}
	
	void setEndDate(Date d) {
		endDate = d;
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
	LocalTime getStartTime() {
		return startTime;
	}
	
	LocalTime getEndTime() {
		return endTime;
	}
	
	Date getStartDate() {
		return startDate;
	}
	
	Date getEndDate() {
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
