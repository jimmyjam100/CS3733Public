package edu.wpi.cs.yildun.demo;

import edu.wpi.cs.yidun.model.Timeslot;

public class CreateMeetingResponse {
	Timeslot timeslot;
	int httpCode;
	
	public CreateMeetingResponse(Timeslot timeslot, int code) {
		this.timeslot = timeslot;
		this.httpCode = code;
	}
	
	public String toString() {
		return "response(" + timeslot + ")";
	}

}
