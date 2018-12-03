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
	
	public boolean closeWeekday(int weekday){
		boolean allClosed = true;
		for (Week w : weeks) {
			if (!w.closeDay(weekday)) {
				allClosed = false;
			}
		}
		return allClosed;
	}
	
	public boolean closeTime(LocalTime t){
		boolean allClosed = true;
		for (Week w : weeks) {
			if (!w.closeTime(t)) {
				allClosed = false;
			}
		}
		return allClosed;
	}

	//setters
	public void setStartTime(LocalTime t) {
		startTime = t;
	}
	
	public void setEndTime(LocalTime t) {
		endTime = t;
	}
	
	public void setStartDate(Date d) {
		startDate = d;
	}
	
	public void setEndDate(Date d) {
		endDate = d;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public void setId(String s) {
		id = s;
	}
	
	public void setPassword(String s) {
		password = s;
	}
	
	public void setMinPerTimeslot(int i) {
		minPerTimeslot = i;
	}
	
	//getters
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public ArrayList<Week> getWeeks() {
		return weeks;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getMinPerTimeslot() {
		return minPerTimeslot;
	}
}
