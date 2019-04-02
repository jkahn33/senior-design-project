package senior.design.group10.tests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import senior.design.group10.application.Application;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.user.ActiveUser;
import senior.design.group10.objects.user.Users;
import senior.design.group10.objects.sent.SentCalendar;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.tv.Calendar;
import senior.design.group10.service.CalendarService;
import senior.design.group10.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class CalendarTest {
	@Autowired
	CalendarService calendarService;
	SentCalendar newEventA;
	SentCalendar newEventB;
	List<Calendar> returned;
	ResponseObject response;
	
	@Before
	public void before() {
		newEventA = new SentCalendar("Event A", "2007-6-11 10:10:00.0", "99991");
		newEventB = new SentCalendar("Event B", "2007-6-12 10:10:00.0", "99991");
		returned = null;
		response = null;
	}
	@Test
	public void test() {
		response = calendarService.createNewEvent(newEventA);
		assertTrue(response.isSuccess());
		response = calendarService.createNewEvent(newEventB);
		assertTrue(response.isSuccess());
		
		//bordering timestamp (00:00:00.00)
		returned = calendarService.getEventsByDate(Timestamp.valueOf("2007-6-12 00:00:00.0"));
		assertTrue(returned.size() == 1);
		assertTrue(returned.get(0).getEventName() == "Event B");
	}
	@After
	public void after() {
		calendarService = null;
		newEventA = null;
		newEventB = null;
		returned = null;
		response = null;
	}
}