package edu.wpi.cs.yidun.model;

import java.util.ArrayList;

public class Meeting {

	ArrayList<String> users;
	String password;
	String id;
	
	public Meeting(String password, String id) {
		users = new ArrayList<String>();
		this.password = password;
		this.id = id;
	}
	
	
	boolean addUser(String user) {
		if (users.contains(user)) return false;
		users.add(user);
		return true;
	}
	
	
	boolean removeUser(String user) {
		if (!users.contains(user)) return false;
		users.remove(user);
		return true;
	}
	
	//setters
	void setPassword(String s) {
		password = s;
	}
	
	void setId(String s) {
		id = s;
	}
	
	//getters
	String getPassword() {
		return password;
	}
	
	String getId() {
		return id;
	}
	
	ArrayList<String> getUsers() {
		return users;
	}
}
