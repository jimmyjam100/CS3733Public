package edu.wpi.cs.yildun.demo;

public class DeleteScheduleRespnse {
	int httpCode;
	public DeleteScheduleRespnse(int code){
		this.httpCode = code;
	}
	public String toString() {
		return "Response("+this.httpCode+")";
	}

}
