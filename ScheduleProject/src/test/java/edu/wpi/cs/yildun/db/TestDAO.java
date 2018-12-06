package edu.wpi.cs.yildun.db;

import java.time.LocalTime;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.yidun.db.ScheduleDAO;
import edu.wpi.cs.yidun.model.Day;
import edu.wpi.cs.yidun.model.Schedule;
import edu.wpi.cs.yidun.model.Timeslot;
import edu.wpi.cs.yidun.model.Week;
import edu.wpi.cs.yildun.demo.LambdaFunctionHandler;

public class TestDAO {

	//TODO !Add more test cases!
	
	Schedule schedule;
	ScheduleDAO dao;
	
	@SuppressWarnings("deprecation")
	@Before
	public void initAll() {
		Date startDate = new Date();
		Date endDate = new Date();
		endDate.setDate(startDate.getDate()+1);
		schedule = LambdaFunctionHandler.newSchedule("Test", startDate, endDate,
				LocalTime.parse("09:00"), LocalTime.parse("16:00"), 60);
		
		dao = new ScheduleDAO();
	}
	
	@Test
	public void testAddAndGetSchedule() {
		try {
			dao.addSchedule(schedule);
			int ts1 = 0, ts2 = 0;
			for (Week week : schedule.getWeeks()) {
				for (Day day : week.getDays()) {
					for (@SuppressWarnings("unused") Timeslot ts : day.getTimeslots()) {
						ts1++;
					}
				}
			}
			
			Schedule schedule2 = dao.getSchedule(schedule.getId());
			for (Week week : schedule2.getWeeks()) {
				for (Day day : week.getDays()) {
					for (@SuppressWarnings("unused") Timeslot ts : day.getTimeslots()) {
						ts2++;
					}
				}
			}
			Assert.assertEquals(ts1, ts2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
}
