package senior.design.group10.tests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import senior.design.group10.objects.sent.ReservableIdWrapper;
import senior.design.group10.objects.sent.SentBreakoutReservation;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.sent.SentReservable;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.user.Users;
import senior.design.group10.service.BreakoutService;
import senior.design.group10.service.ReservablesService;
import senior.design.group10.service.UserService;

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
	private ReservableIdWrapper roomList;
	private Timestamp timeNew;
	private ResponseObject response;
	private List<String> rList;
	
	@Before
	public void before() {
		rList = Arrays.asList("A", "B");
		roomList = new ReservableIdWrapper(rList);
		reservable = new SentReservable("Breakout", "A");
		reservablesService.saveNewReservable(reservable);
		reservable = new SentReservable("Breakout", "B");
		reservablesService.saveNewReservable(reservable);
	}
	@Test
	public void test() {
		reservationNew = new SentBreakoutReservation("00001", "Breakout", 
				roomList, "desc", "2007-6-11 10:10:10.0", 
				"2007-6-11 12:10:10.0", "5", "comments");
		response = service.addBreakRes(reservationNew);
		assertTrue(response.isSuccess());
	}
	@After
	public void after() {
		/*
		reservable = new SentReservable("Breakout", "A");
		reservablesService.removeReservable(reservable);
		reservable = new SentReservable("Breakout", "B");
		reservablesService.removeReservable(reservable);
		reservable = new SentReservable("Breakout", "C");
		reservablesService.removeReservable(reservable);
		*/
		reservable = null;
		reservationExisting = null;
		reservationNew = null;
		timeExisting = null;
		timeNew = null;
		response = null;
	}

}