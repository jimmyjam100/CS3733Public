package edu.wpi.cs.yildun.demo;

public class CancelMeetingResponse {
	int httpCode;
	
	public CancelMeetingResponse(int code) {
		this.httpCode = code;
	}
	
	public String toString() {
		return "response("+this.httpCode+")";
	}

}
