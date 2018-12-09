package edu.wpi.cs.yildun.demo;

public class GetAllOpenTimeslotsRequest {
	int id;
	
	public GetAllOpenTimeslotsRequest(int id) {
		this.id = id;
	}
	
	public String toSting() {
		return "GetAllOpenTimeslotsRequest(" + this.id + ")";
	}

}
