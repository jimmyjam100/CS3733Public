package rds;

import java.sql.*;

public class ScheduleDAO {

	java.sql.Connection conn;
	
	
    public ScheduleDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    /*
    public Schedule getSchedule(int id) throws Exception {
    	try {
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules where id=?;");
    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();
    		Schedule s = rs.next();
    		rs.close();
    		ps.close();
    		return s;
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Failed to get the Schedule requested");
    	}
    }
	*/
    
    /*public List<TimeSlot> getTimeSlots(Schedule s) {
    	try {
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM TimeSlots where schedule_id=?;");
    		ps.setInt(1, s.getId());
    		ResultSet rs = ps.executeQuery();
    		List<TimeSlot> slots = new ArrayList<TimeSlot>();
    		while (rs.next()) {
    			slots.add(new TimeSlot(rs));
    		}
    		rs.close();
    		ps.close();
    		return slots;
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Unable to retrieve the time slots for the given schedule.");
    	}
    }
    */
    
    /*
    public Constant getConstant(String name) throws Exception {
        
        try {
            Constant constant = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Constants WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                constant = generateConstant(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return constant;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting constant: " + e.getMessage());
        }
    }
    */
}