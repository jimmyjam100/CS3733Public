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
	
	
	public boolean addUser(String user) {
		if (users.contains(user)) return false;
		users.add(user);
		return true;
	}
	
	
	public boolean removeUser(String user) {
		if (!users.contains(user)) return false;
		users.remove(user);
		return true;
	}
	
	//setters
	public void setPassword(String s) {
		password = s;
	}
	
	public void setId(String s) {
		id = s;
	}
	
	//getters
	public String getPassword() {
		return password;
	}
	
	public String getId() {
		return id;
	}
	
	public ArrayList<String> getUsers() {
		return users;
	}
}
