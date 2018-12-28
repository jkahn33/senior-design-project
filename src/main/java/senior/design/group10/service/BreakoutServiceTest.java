package senior.design.group10.service;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import senior.design.group10.application.Application;
import senior.design.group10.dao.BreakoutReservationDAO;
import senior.design.group10.dao.ReservablesDAO;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.equipment.ReservableKey;
import senior.design.group10.objects.equipment.Reservables;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentBreakoutReservation;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.sent.SentReservable;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.user.Users;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class BreakoutServiceTest {
	//private BreakoutReservationDAO breakoutReservationDAO;
	//private ReservablesDAO reservablesDAO;
	//private UsersDAO usersDAO;
	
	@Autowired
	private BreakoutService service;//  = new BreakoutService(usersDAO, breakoutReservationDAO, reservablesDAO);
	@Autowired
	private ReservablesService reservablesService;
	@Autowired
	private UserService userService;
	private SentReservable reservable;
	private SentBreakoutReservation reservationExisting;
	private SentBreakoutReservation reservationNew;
	private Timestamp timeExisting;
	private Timestamp timeNew;
	private ResponseObject response;
	
	@Before
	public void before() {
		//Creating the shared reservable
		reservable = new SentReservable("Breakout", "A");
		reservablesService.saveNewReservable(reservable);
		//Reserving it from 10:10 AM to 11:10 AM
		timeExisting = Timestamp.valueOf("2007-6-11 10:10:10.0");
		reservationExisting = new SentBreakoutReservation("12345", "Breakout", "A", "test",
															timeExisting, "1:00","25", "test");
		service.addBreakRes(reservationExisting);
	}
	
	@Test
	public void test() {
		//Requesting a reservation from 10:15 AM to 11:15 AM
		//(the start of this reservation is contained in the existing reservation)
		timeNew = Timestamp.valueOf("2007-6-11 10:15:10.0");
		reservationNew = new SentBreakoutReservation("12345", "Breakout", "A", "test", 
														timeNew, "1:00", "25", "test");
		response = service.addBreakRes(reservationNew);
		System.out.println(response.getMessage());
		//This request should fail.
		assertFalse(response.isSuccess());
	}

	@After
	public void after() {
		reservablesService.removeReservable(reservable);
		reservable = null;
		reservationExisting = null;
		reservationNew = null;
		timeExisting = null;
		timeNew = null;
		response = null;
	}

}
