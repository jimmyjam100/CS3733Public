package edu.wpi.cs.yildun.demo;

public class ExtendStartResponse {
	
	int httpCode;
	
	public ExtendStartResponse(int code){
		httpCode = code;
	}
	
	public String toString() {
		return "response(" + httpCode + ")";
	}

}
