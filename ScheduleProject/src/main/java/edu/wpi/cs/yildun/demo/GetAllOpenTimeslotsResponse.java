package edu.wpi.cs.yildun.demo;

import java.util.ArrayList;

import edu.wpi.cs.yidun.model.Schedule;
import edu.wpi.cs.yidun.model.Timeslot;

public class GetAllOpenTimeslotsResponse {
	int httpCode;
	Schedule sched;
	public GetAllOpenTimeslotsResponse(Schedule sched, int code){
		this.sched = sched;
		this.httpCode = code;
	}
	public String toString() {
		return "Response(" + this.sched.toString() + ", "+this.httpCode+")";
	}

}
