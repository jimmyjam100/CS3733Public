package edu.wpi.cs.yildun.demo;

import java.util.ArrayList;

import edu.wpi.cs.yidun.model.Timeslot;

public class GetAllOpenTimeslotsResponse {
	int httpCode;
	ArrayList<Timeslot> timeslots;
	public GetAllOpenTimeslotsResponse(ArrayList<Timeslot> timeslots, int code){
		this.timeslots = timeslots;
		this.httpCode = code;
	}
	public String toString() {
		return "Response(" + this.timeslots.toString() + ", "+this.httpCode+")";
	}

}
