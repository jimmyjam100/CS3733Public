package edu.wpi.cs.yildun.demo;

public class OpenTimeResponse {
	int httpCode;
	public OpenTimeResponse(int code){
		this.httpCode = code;
	}
	public String toString() {
		return "Response("+this.httpCode+")";
	}

}
