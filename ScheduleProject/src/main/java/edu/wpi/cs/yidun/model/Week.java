package edu.wpi.cs.yidun.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Week {

	ArrayList<Day> days;
	int weekNum;
	
	public Week(int weekNum) {
		days = new ArrayList<Day>();
		weekNum = this.weekNum;
	}
	
	boolean closeTime(LocalTime t){
		boolean allClosed = true;
		for (Day d : days) {
			if (!d.closeTime(t)) {
				allClosed = false;
			}
		}
		return allClosed;
	}
	
	boolean closeDay(int weekday){
		return days.get(weekday).closeDay();
	}
	
	//setters
	void setWeekNum(int i) {
		weekNum = i;
	}
	
	//getters
	int getWeekNum() {
		return weekNum;
	}
	
	ArrayList<Day> getDays(){
		return days;
	}
}
