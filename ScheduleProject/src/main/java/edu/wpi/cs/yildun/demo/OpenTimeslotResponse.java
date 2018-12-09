package edu.wpi.cs.yildun.demo;

public class OpenTimeslotResponse {
	int httpCode;
	public OpenTimeslotResponse(int code){
		this.httpCode = code;
	}
	public String toString() {
		return "Response("+this.httpCode+")";
	}

}
