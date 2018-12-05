package edu.wpi.cs.yidun.model;

import java.util.ArrayList;

public class MeetingScheduler {
	
	ArrayList<Schedule> schedules;
	
	public MeetingScheduler() {
		schedules = new ArrayList<Schedule>();
	}
	
	boolean createSchedule(Schedule s){
		if (schedules.contains(s)) {
			return false;
		}
		else {
			schedules.add(s);
			return true;
		}
	}
	
	boolean deleteSchedule(int id) {
		for (int i = 0; i < schedules.size(); i++) {
			if (schedules.get(i).id == id) {
				schedules.remove(i);
				return true;
			}
		}
		return false;
	}
	
	ArrayList<Schedule> getSchedule(){
		return schedules;
	}

}
