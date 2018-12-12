package edu.wpi.cs.yildun.demo;

public class OpenDayResponse {
	int httpCode;
	public OpenDayResponse(int code){
		this.httpCode = code;
	}
	public String toString() {
		return "Response("+this.httpCode+")";
	}

}
