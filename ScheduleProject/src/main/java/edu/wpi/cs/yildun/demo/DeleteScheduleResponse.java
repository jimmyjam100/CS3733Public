package edu.wpi.cs.yildun.demo;

public class DeleteScheduleResponse {
	int httpCode;
	public DeleteScheduleResponse(int code){
		this.httpCode = code;
	}
	public String toString() {
		return "Response("+this.httpCode+")";
	}

}
