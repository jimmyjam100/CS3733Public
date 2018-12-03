package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Week {

	ArrayList<Day> days;
	int weekNum;
	String id;
	
	public Week(int weekNum, String id) {
		days = new ArrayList<Day>();
		this.weekNum = weekNum;
		this.id = id;
	}
	
	public boolean closeTime(LocalTime t){
		boolean allClosed = true;
		for (Day d : days) {
			if (!d.closeTime(t)) {
				allClosed = false;
			}
		}
		return allClosed;
	}
	
	public boolean closeDay(int weekday){
		return days.get(weekday).closeDay();
	}
	
	//setters
	public void setWeekNum(int i) {
		weekNum = i;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	//getters
	public int getWeekNum() {
		return weekNum;
	}
	
	public ArrayList<Day> getDays(){
		return days;
	}
	
	public String getId() {
		return id;
	}
	
	public void addDay(Day d) {
		days.add(d);
	}
}
