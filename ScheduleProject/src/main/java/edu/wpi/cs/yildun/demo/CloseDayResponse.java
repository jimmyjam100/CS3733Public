package edu.wpi.cs.yildun.demo;

public class CloseDayResponse {
	int httpCode;
	public CloseDayResponse(int code){
		this.httpCode = code;
	}
	public String toString() {
		return "Response("+this.httpCode+")";
	}

}
