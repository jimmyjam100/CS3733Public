package edu.wpi.cs.yildun.demo;

public class ExtendEndResponse {
	
	int httpCode;
	
	public ExtendEndResponse(int code){
		httpCode = code;
	}
	
	public String toString() {
		return "response(" + httpCode + ")";
	}

}
