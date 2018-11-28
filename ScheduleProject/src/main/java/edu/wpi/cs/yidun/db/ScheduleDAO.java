package edu.wpi.cs.yidun.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.yidun.model.Schedule;

public class ScheduleDAO {

	java.sql.Connection conn;

    public ScheduleDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    
    public boolean addSchedule(Schedule sched) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE date = ?;");
            ps.setDate(1, (Date) sched.date);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Schedule c = generateSchedule(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO Schedules (date) values(?);");
            ps.setDate(1,  (Date) sched.date);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
    
    private Schedule generateSchedule(ResultSet resultSet) throws Exception {
        Date d  = resultSet.getDate("date");
        return new Schedule(d);
    }

}