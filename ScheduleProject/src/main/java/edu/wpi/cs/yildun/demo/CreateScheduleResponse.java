package edu.wpi.cs.yildun.demo;
import edu.wpi.cs.yidun.model.Timeslot;

import java.util.ArrayList;
import java.util.List;

public class CreateScheduleResponse {
	String name;
	List<List<Timeslot>> timeslotList;
	int startingIndex;
	int httpCode;
	
	public CreateScheduleResponse (String name, List<List<Timeslot>> timeslotList, int startingIndex, int code) {
		this.name = name;
		this.timeslotList = timeslotList;
		this.startingIndex = startingIndex;
		this.httpCode = code;
	}
	public CreateScheduleResponse (String name, int code) {
		this.name = name;
		this.timeslotList = new ArrayList<List<Timeslot>>();
		this.startingIndex = 0;
		this.httpCode = code;
	}
	
	public String toString() {
		return "Response(" + startingIndex + ")";
	}

}
