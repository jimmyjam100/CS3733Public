package edu.wpi.cs.yildun.demo;

public class CloseTimeResponse {
	int httpCode;
	public CloseTimeResponse(int code){
		this.httpCode = code;
	}
	public String toString() {
		return "Response("+this.httpCode+")";
	}

}
