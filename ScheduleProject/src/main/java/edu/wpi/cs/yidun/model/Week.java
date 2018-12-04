package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Week {

	ArrayList<Day> days;
	int weekNum;
	
	public Week(int weekNum) {
		days = new ArrayList<Day>();
		this.weekNum = weekNum;
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
	public void setDays(ArrayList<Day> d) {
		this.days = d;
	}
	
	//getters
	public int getWeekNum() {
		return weekNum;
	}
	
	public ArrayList<Day> getDays(){
		return days;
	}
	
	public void addDay(Day d) {
		days.add(d);
	}
}
