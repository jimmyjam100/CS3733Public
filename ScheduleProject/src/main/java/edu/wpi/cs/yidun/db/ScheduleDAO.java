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
    		s.setId(id);
    		for (Week w : s.getWeeks()) {
    			for (Day d : w.getDays()) {
    				Date date = new Date(d.getDate().getTime());
    				for (Timeslot ts : d.getTimeslots()) {
    					 PreparedStatement slotInsert = conn.prepareStatement(
    							 "INSERT INTO Timeslots (scheduleID, slotDate, startTime, isOpen, user, pass)"+
    							 "VALUES (?, ?, ?, ?, ?, ?)");
    					 
    					 slotInsert.setInt(1, id);
    					 slotInsert.setDate(2, date);
    					 slotInsert.setString(3, ts.getTime().toString());
    					 slotInsert.setString(4, ts.isOpen()?"Y":"N");
    					 slotInsert.setString(5, ts.getUser());
    					 slotInsert.setString(6, ts.getPassword());
    					 
    					 slotInsert.executeUpdate();
    					 
    				    ResultSet slotID = slotInsert.getGeneratedKeys();
    				    if (rs.next()) {
    				    	ts.setId(rs.getInt("id"));
    				    }
    				    slotID.close();
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
    		
    		Schedule schedule = new Schedule(new java.util.Date(startDate.getTime()), new java.util.Date(endDate.getTime()), startTime, endTime, name, password, minPerTimeslot);
    		schedule.setId(scheduleSet.getInt("id"));
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
    			timeslot.makeMeeting(timeslotSet.getString("user"), timeslotSet.getString("pass"));
    			timeslot.setId(timeslotSet.getInt("id"));
    			
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
    /*
    public boolean passMatch(int id, boolean schedule, String password) throws Exception {
    	//No set for table name
    	PreparedStatement ps = conn.prepareStatement("SELECT pass FROM " + (schedule?"Schedules":"Timeslots") + " WHERE id=?");
    	ps.setInt(1, id);
    	ResultSet rs = ps.executeQuery();
    	
    	if (rs.next()) {
    		return rs.getString("pass").equals(password);
    	}
    	
    	rs.close();
    	ps.close();
    	return false;
    }
    */
    
    //TODO addTimeslot(int scheudleID, Timeslot ts)
    
    public Timeslot getTimeslot(int id) throws Exception {
    	PreparedStatement ps = conn.prepareStatement("Select * FROM Timeslots WHERE id=?");
    	ps.setInt(1, id);
    	ResultSet rs = ps.executeQuery();
    	if (rs.next()) {
    		Date date = new Date(rs.getDate("slotDate").getTime());
			LocalTime time = LocalTime.parse(rs.getString("startTime"));
			boolean open = rs.getString("isOpen").equals("Y");
			Timeslot timeslot = new Timeslot(open, time);
			timeslot.makeMeeting(rs.getString("user"), rs.getString("pass"));
			timeslot.setId(rs.getInt("id"));
			rs.close();
			ps.close();
			return timeslot;
    	}
    	else {
    		throw new Exception("No such Timeslot could be retrieved");
    	}
    }
    public void updateTimeslot(Timeslot t) throws Exception {
    	PreparedStatement ps = conn.prepareStatement("UPDATE Timeslots SET isOpen=?, user=?, pass=? WHERE id=?;");
    	ps.setString(1, t.isOpen()?"Y":"N");
    	ps.setString(2, t.getUser());
    	ps.setString(3, t.getPassword());
    	ps.setInt(4, t.getId());
    	
    	ps.executeUpdate();
    	ps.close();
    }
}