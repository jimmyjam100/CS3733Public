package edu.wpi.cs.yidun.db;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

import edu.wpi.cs.yidun.model.Day;
import edu.wpi.cs.yidun.model.Pair;
import edu.wpi.cs.yidun.model.Schedule;
import edu.wpi.cs.yidun.model.Timeslot;
import edu.wpi.cs.yidun.model.Week;

public class ScheduleDAO {
	//The connection to the database
	Connection conn;

	//On initializing the class, grab a connection to the database
    public ScheduleDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    /**
     * Attempts to add a given schedule to the database
     * @param s The schedule to add to the database
     * @throws Exception
     */
	public void addSchedule(Schedule s) throws Exception {
    	PreparedStatement scheduleInsert = conn.prepareStatement(
    			"INSERT INTO Schedules (scheduleName, pass, startTime, endTime, created, startDate, endDate, minPerTimeSlot)"
    			+ "values (?, ?, ?, ?, NOW(),  ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
    	
    	scheduleInsert.setString(1, s.getName());
    	scheduleInsert.setString(2, s.getPassword());
    	scheduleInsert.setString(3, s.getStartTime().toString());
    	scheduleInsert.setString(4, s.getEndTime().toString());
    	scheduleInsert.setDate(5, new Date(s.getStartDate().getTime()));
    	scheduleInsert.setDate(6, new Date(s.getEndDate().getTime()));
    	scheduleInsert.setInt(7, s.getMinPerTimeslot());
    	
    	scheduleInsert.executeUpdate(); //Save all the Schedule columns to the Schedule Table
    	int id = -1;
    	//This is code to retrieve the id generated by the AUTO_INCREMENT instruction
    	PreparedStatement getScheduleID = conn.prepareStatement("SELECT LAST_INSERT_ID();");
    	ResultSet scheduleIDSet = getScheduleID.executeQuery();
    	if (scheduleIDSet.next()) {
    		id = scheduleIDSet.getInt(1);
    	}
    	//Close resources no longer being used
    	scheduleIDSet.close();
    	getScheduleID.close();
    	scheduleInsert.close();
    	
    	if (id >= 0) {
    		s.setId(id);
    		for (Week w : s.getWeeks()) {
    			for (Day d : w.getDays()) {
    				Date date = toSQLDate(d.getDate());
    				for (Timeslot ts : d.getTimeslots()) {
    					//Add every timeslot to the database
    					addTimeslot(id, ts, date);
    				}
    			}
    		}
    	}
    	else {
    		//Could not find the ID the schedule was generated with
    		throw new Exception("Could not obtain a valid ID for the Schedule.");
    	}
    }
    
	/**
	 * Gets a schedule back from the database
	 * @param id the id of the schedule within the database
	 * @return the model entity Schedule with all fields correct
	 * @throws Exception
	 */
    public Schedule getSchedule(int id) throws Exception {
    	PreparedStatement scheduleInfo = conn.prepareStatement("SELECT * FROM Schedules WHERE id=?;");
    	scheduleInfo.setInt(1, id);
    	//Execute the query
    	ResultSet scheduleSet = scheduleInfo.executeQuery();
    	
    	if (scheduleSet.next()) {
    		Date startDate = scheduleSet.getDate("startDate");
    		Date endDate = scheduleSet.getDate("endDate");
    		LocalTime startTime = LocalTime.parse(scheduleSet.getString("startTime"));
    		LocalTime endTime = LocalTime.parse(scheduleSet.getString("endTime"));
    		String name = scheduleSet.getString("scheduleName");
    		String password = scheduleSet.getString("pass");
    		int minPerTimeslot = scheduleSet.getInt("minPerTimeslot");
    		//Save all Schedule fields
    		Schedule schedule = new Schedule(startDate, endDate, startTime, endTime, name, password, minPerTimeslot);
    		schedule.setId(scheduleSet.getInt("id"));
    		ArrayList<Week> weeks = schedule.getWeeks();
    		weeks.clear();
    		
    		PreparedStatement timeslotInfo = conn.prepareStatement(
    				"SELECT * FROM Timeslots WHERE scheduleID=? ORDER BY slotDate, startTime;");
    		timeslotInfo.setInt(1, id);
    		ResultSet timeslotSet = timeslotInfo.executeQuery();
    		
    		Day currentDay = new Day(0, startDate); //This init shouldn't matter
    		Week currentWeek = new Week(0);
    		int dayNum = startDate.toLocalDate().getDayOfWeek().getValue()-1,
    				weekNum = 1;
    		
    		weeks.add(currentWeek);
    		
    		while (timeslotSet.next()) {
    			//Get all timeslots and add them to week
    			Date date = timeslotSet.getDate("slotDate");
    			LocalTime time = LocalTime.parse(timeslotSet.getString("startTime"));
    			boolean open = timeslotSet.getString("isOpen").equals("Y");
    			Timeslot timeslot = new Timeslot(open, time);
    			timeslot.makeMeeting(timeslotSet.getString("user"), timeslotSet.getString("pass"));
    			timeslot.setId(timeslotSet.getInt("id"));
    			
    			if (time.equals(startTime)) {
    				if (dayNum>= 5) { //The start of a new week
    					dayNum = 0;
    					currentWeek = new Week(weekNum++);
    					weeks.add(currentWeek);
    				}
    				currentDay = new Day(dayNum++, date); //Initialize the new day
    				currentWeek.addDay(currentDay); //Add the day onto the week
    			}
    			
    			currentDay.getTimeslots().add(timeslot);	
    		}
    		//Close resources
    		timeslotSet.close();
    		timeslotInfo.close();
    		scheduleSet.close();
    		scheduleInfo.close();
    		
    		return schedule;
    	}
    	else {
    		//Schedule not found
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
    
    /**
     * Puts a Timeslot into the database
     * @param scheduleID The associated scheduleID
     * @param ts The timeslot to add
     * @param date The date the timeslot takes place on
     * @throws Exception
     */
    public void addTimeslot(int scheduleID, Timeslot ts, Date date) throws Exception {
    	 PreparedStatement slotInsert = conn.prepareStatement(
				 "INSERT INTO Timeslots (scheduleID, slotDate, startTime, isOpen, user, pass)"+
				 "VALUES (?, ?, ?, ?, ?, ?)");
		 
		 slotInsert.setInt(1, scheduleID);
		 slotInsert.setDate(2, date);
		 slotInsert.setString(3, ts.getTime().toString());
		 slotInsert.setString(4, ts.isOpen()?"Y":"N");
		 slotInsert.setString(5, ts.getUser());
		 slotInsert.setString(6, ts.getPassword());
		 //Execute the update
		 slotInsert.executeUpdate();
		 
		 //Retrieve the generated ID
		 PreparedStatement getTimeslotID = conn.prepareStatement("SELECT LAST_INSERT_ID();");
	     ResultSet timeslotIDSet = getTimeslotID.executeQuery();
	     if (timeslotIDSet.next()) {
	    	 //Update the timeslot's id
	    	 ts.setId(timeslotIDSet.getInt(1));
	     }
	     timeslotIDSet.close();
	     getTimeslotID.close();
		 slotInsert.close();
    }
    
    //Retrieve a timeslot from the database
    public Timeslot getTimeslot(int id) throws Exception {
    	PreparedStatement ps = conn.prepareStatement("Select * FROM Timeslots WHERE id=?");
    	ps.setInt(1, id);
    	ResultSet rs = ps.executeQuery();
    	if (rs.next()) {
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
    		//Could not retrieve the given id
    		throw new Exception("No such Timeslot could be retrieved");
    	}
    }
    /**
     * Updates the modifiable information of a Schedule already in the database
     * @param s the instance of the schedule with changed data
     * @throws Exception
     */
    public void updateSchedule(Schedule s) throws Exception {
    	PreparedStatement updateSchedule = conn.prepareStatement(
    			"UPDATE Schedules SET startDate=?, endDate=?");
    	
    	updateSchedule.setDate(1, new Date(s.getStartDate().getTime()));
    	updateSchedule.setDate(2, new Date(s.getEndDate().getTime()));
    	
    	updateSchedule.executeUpdate();
    	updateSchedule.close();
    }
    /**
     * Updates the modifiable information of a Timeslot already in the database
     * @param t The instance of the timeslot with changed data
     * @throws Exception
     */
    public void updateTimeslot(Timeslot t) throws Exception {
    	PreparedStatement ps = conn.prepareStatement("UPDATE Timeslots SET isOpen=?, user=?, pass=? WHERE id=?;");
    	ps.setString(1, t.isOpen()?"Y":"N");
    	ps.setString(2, t.getUser());
    	ps.setString(3, t.getPassword());
    	ps.setInt(4, t.getId());
    	//Execute the update
    	ps.executeUpdate();
    	ps.close();
    }
    /**
     * Get the names and ids of all schedules
     * @return An ArrayList<Pair<String, Integer>> containing all names, ids
     * @throws Exception
     */
    public ArrayList<Pair<String, Integer>> getAllSchedules() throws Exception{
    	return getSchedulesOlder(0); //Call older with param 0 (True for all schedules)
    }
    /**
     * Gets all the schedules created more recently than some N hours
     * @param hour the maximum number of hours the schedule has existed
     * @return An ArrayList<Pair<String, Integer>> containing matching Schedules' names/ids
     * @throws Exception
     */
    public ArrayList<Pair<String, Integer>> getSchedulesEarlier(int hour) throws Exception {
    	PreparedStatement ps = conn.prepareStatement(
    			"SELECT scheduleName, id FROM Schedules WHERE"
    			+ " created > (NOW() - INTERVAL ? HOUR);");
    	
    	ps.setInt(1, hour);
    	ResultSet rs = ps.executeQuery();
    	ArrayList<Pair<String, Integer>> info = new ArrayList<Pair<String, Integer>>();
    	while (rs.next()) {
    		info.add(new Pair<String, Integer>(rs.getString("scheduleName"), rs.getInt("id")));
    	}
    	rs.close();
    	ps.close();
    	return info;
    }
    /**
     * Gets all the schedules created more recently than some N hours
     * @param hour the maximum number of hours the schedule has existed
     * @return An ArrayList<Pair<String, Integer>> containing matching Schedules' names/ids
     * @throws Exception
     */
    public ArrayList<Pair<String, Integer>> getSchedulesOlder(int days) throws Exception {
    	PreparedStatement ps = conn.prepareStatement(
    			"SELECT scheduleName, id FROM Schedules WHERE"
    			+ " created < TIMESTAMPADD(DAY, ?, NOW());");
    	//Subtract days
    	ps.setInt(1, -days);
    	ResultSet rs = ps.executeQuery();
    	ArrayList<Pair<String, Integer>> info = new ArrayList<Pair<String, Integer>>();
    	while (rs.next()) {
    		info.add(new Pair<String, Integer>(rs.getString("scheduleName"), rs.getInt("id")));
    	}
    	rs.close();
    	ps.close();
    	return info;
    }
    
    /**
     * Deletes a schedule with the given ID from the database (Along with its associated timeslots)
     * @param id the ID of the schedule to delete
     * @throws Exception
     */
    public boolean deleteSchedule(int id) throws Exception {
    	PreparedStatement ps1 = conn.prepareStatement("DELETE FROM Schedules WHERE id=?;");
    	ps1.setInt(1, id);
    	int deleted = ps1.executeUpdate();
    	ps1.close();
    	
    	PreparedStatement ps2 = conn.prepareStatement("DELETE FROM Timeslots WHERE scheduleID=?;");
    	ps2.setInt(1, id);
    	ps2.executeUpdate();
    	ps2.close();
    	
    	return deleted>0;
    }
    
    /**
     * Converts java.util.Date to java.sql.Date
     * @param date The java.util.Date to Convert
     * @return The corresponding java.sql.Date
     */
    public Date toSQLDate(java.util.Date date) {
    	return new Date(date.getTime());
    }
}