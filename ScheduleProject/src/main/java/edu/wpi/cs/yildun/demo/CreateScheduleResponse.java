package edu.wpi.cs.yildun.demo;
import edu.wpi.cs.yidun.model.Schedule;
import edu.wpi.cs.yidun.model.Timeslot;

import java.util.ArrayList;
import java.util.List;

public class CreateScheduleResponse {
	Schedule sched;
	int httpCode;
	
	public CreateScheduleResponse (Schedule sched, int code) {
		this.httpCode = code;
		this.sched = sched;
	}
	
	public String toString() {
		return "Response(" + sched.toString() + ")";
	}

}
