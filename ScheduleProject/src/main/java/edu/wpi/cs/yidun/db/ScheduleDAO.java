package edu.wpi.cs.yidun.db;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import edu.wpi.cs.yidun.model.Day;
import edu.wpi.cs.yidun.model.Schedule;
import edu.wpi.cs.yidun.model.Timeslot;
import edu.wpi.cs.yidun.model.Week;

public class ScheduleDAO {

	java.sql.Connection conn;

    public ScheduleDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
	public void addSchedule(Schedule s) throws Exception {
    	PreparedStatement scheduleInsert = conn.prepareStatement(
    			"INSERT INTO Schedules (scheduleName, pass, startTime, endTime, created, startDate, endDate, minPerTimeSlot)"
    			+ "values (?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
    	
    	scheduleInsert.setString(1, s.getName());
    	scheduleInsert.setString(2, s.getPassword());
    	scheduleInsert.setString(3, s.getStartTime().toString());
    	scheduleInsert.setString(4, s.getEndTime().toString());
    	scheduleInsert.setDate(5, Date.valueOf(LocalDate.now()));
    	scheduleInsert.setDate(6, new Date(s.getStartDate().getTime()));
    	scheduleInsert.setDate(7, new Date(s.getEndDate().getTime()));
    	scheduleInsert.setInt(8, s.getMinPerTimeslot());
    	
    	scheduleInsert.executeUpdate();
    	int id = -1;
    	ResultSet rs = scheduleInsert.getGeneratedKeys();
    	if (rs.next()) {
    		id = rs.getInt("id");
    	}
    	rs.close();
    	scheduleInsert.close();
    	
    	if (id >= 0) {
    		//TODO s.setId(id);
    		for (Week w : s.getWeeks()) {
    			for (Day d : w.getDays()) {
    				Date date = new Date(d.getDate().getTime());
    				for (Timeslot ts : d.getTimeslots()) {
    					 PreparedStatement slotInsert = conn.prepareStatement(
    							 "INSERT INTO Timeslots (scheduleID, slotDate, startTime, isOpen, user, pass)"+
    							 "VALUES (?, ?, ?, ?, ?, ?)");
    					 
    					 slotInsert.setInt(1, id);
    					 slotInsert.setDate(2, date);
    					 slotInsert.setString(3, null/*TODO ts.getTime().toString()*/);
    					 slotInsert.setString(4, null/*TODO ts.isOpen()?"Y":"N"*/);
    					 slotInsert.setString(5, null/*TODO ts.getUser()*/);
    					 slotInsert.setString(6, null /*TODO ts.getPassword()*/);
    					 
    					 slotInsert.executeUpdate();
    					 slotInsert.close();
    				}
    			}
    		}
    	}
    	else {
    		throw new Exception("Could not obtain a valid ID for the Schedule.");
    	}
    }
    
    public Schedule getSchedule(int id) throws Exception {
    	PreparedStatement scheduleInfo = conn.prepareStatement("SELECT * FROM Schedules WHERE id=?;");
    	scheduleInfo.setInt(1, id);
    	ResultSet scheduleSet = scheduleInfo.executeQuery();
    	if (scheduleSet.next()) {
    		Date startDate = scheduleSet.getDate("startDate");
    		Date endDate = scheduleSet.getDate("endDate");
    		LocalTime startTime = LocalTime.parse(scheduleSet.getString("startTime"));
    		LocalTime endTime = LocalTime.parse(scheduleSet.getString("endTime"));
    		String name = scheduleSet.getString("scheduleName");
    		String password = scheduleSet.getString("pass");
    		int minPerTimeslot = scheduleSet.getInt("minPerTimeslot");
    		
    		Schedule schedule = new Schedule(new java.util.Date(startDate.getTime()), new java.util.Date(endDate.getTime()), startTime, endTime, name, /*TODO*/Integer.toString(id), password, minPerTimeslot);
    		ArrayList<Week> weeks = schedule.getWeeks();
    		weeks.clear();
    		
    		PreparedStatement timeslotInfo = conn.prepareStatement(
    				"SELECT * FROM Timeslots WHERE scheduleID=? ORDER BY slotDate, startTime;");
    		timeslotInfo.setInt(1, id);
    		ResultSet timeslotSet = timeslotInfo.executeQuery();
    		
    		Day currentDay = new Day(0, startDate); //This init shouldn't matter
    		Week currentWeek = new Week(0);
    		int dayNum = startDate.toLocalDate().getDayOfWeek().getValue(),
    				weekNum = 1;
    		
    		weeks.add(currentWeek);
    		
    		while (timeslotSet.next()) {
    			Date date = new Date(timeslotSet.getDate("slotDate").getTime());
    			LocalTime time = LocalTime.parse(timeslotSet.getString("startTime"));
    			boolean open = timeslotSet.getString("isOpen").equals("Y");
    			Timeslot timeslot = new Timeslot(open, time);
    			
    			if (time.equals(startTime)) {
    				if (dayNum%5==0) {
    					currentWeek = new Week(weekNum++);
    					weeks.add(currentWeek);
    				}
    				currentDay = new Day(dayNum++, date);
    				currentWeek.addDay(currentDay);
    			}
    			
    			currentDay.getTimeslots().add(timeslot);	
    		}
    		
    		timeslotSet.close();
    		timeslotInfo.close();
    		scheduleSet.close();
    		scheduleInfo.close();
    		
    		return schedule;
    	}
    	else {
    		throw new Exception("No schedule with the given ID was obtainable.");
    	}
    }
    
}