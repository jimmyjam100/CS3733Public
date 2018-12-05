package edu.wpi.cs.yildun.db;

import java.time.LocalTime;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.yidun.db.ScheduleDAO;
import edu.wpi.cs.yidun.model.Schedule;

public class TestDAO {

	Schedule schedule;
	ScheduleDAO dao;
	
	@Before
	public void initAll() {
		schedule = new Schedule(new Date(), new Date(), 
				LocalTime.parse("09:00"), LocalTime.parse("16:00"), "Test", "", 60);
		dao = new ScheduleDAO();
	}
	
	@Test
	public void testAddSchedule() {
		try {
			dao.addSchedule(schedule);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetSchedule() {
		try {
			Assert.assertEquals(schedule, dao.getSchedule(schedule.getId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
