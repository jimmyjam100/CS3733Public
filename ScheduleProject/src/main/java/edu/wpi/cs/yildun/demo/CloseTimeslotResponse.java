package edu.wpi.cs.yildun.demo;

public class CloseTimeslotResponse {
	int httpCode;
	public CloseTimeslotResponse(int code){
		this.httpCode = code;
	}
	public String toString() {
		return "Response("+this.httpCode+")";
	}

}
