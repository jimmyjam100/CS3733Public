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
	
	@SuppressWarnings("deprecation")
	@Test
	public void testScheduleFcns() {
		try {
			Date startDate = new Date();
			Date endDate = new Date();
			endDate.setDate(startDate.getDate()+1);
			Schedule schedule = LambdaFunctionHandler.newSchedule("Test", startDate, endDate,
					LocalTime.parse("09:00"), LocalTime.parse("16:00"), 60);
			
			ScheduleDAO dao = new ScheduleDAO();
			
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
			
			//Assert.assertEquals(dao.getAllSchedules().size(), 1);
			//Assert.assertEquals(dao.getSchedulesEarlier(1).size(), 1);
			Timeslot test = schedule.getWeeks().get(0).getDays().get(0).getTimeslots().get(0);
			test.makeMeeting("a", "b");
			dao.updateTimeslot(test);
			Assert.assertEquals(dao.getTimeslot(test.getId()).getPassword(), test.getPassword());
			Assert.assertTrue(dao.deleteSchedule(schedule.getId()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
	}
}
