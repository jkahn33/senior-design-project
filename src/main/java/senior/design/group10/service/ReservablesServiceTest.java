package senior.design.group10.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import senior.design.group10.application.Application;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentReservable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class ReservablesServiceTest {
	@Autowired
	private ReservablesService service;
	private SentReservable newReservable;
	private ResponseObject response;
	
	@Before
	public void before() {
		newReservable = new SentReservable("Breakout", "1");
	}
	@Test
	public void test() {
		//Adding a new object to the table. Expected to succeed.
		response = service.saveNewReservable(newReservable);
		assertTrue(response.isSuccess());
		//Attempting to add the same object again. Expected to fail.
		response = service.saveNewReservable(newReservable);
		assertFalse(response.isSuccess());
		//Removing the object from the table. Expected to succeed.
		response = service.removeReservable(newReservable);
		assertTrue(response.isSuccess());
		//Removing the same object again. Expected to fail.
		response = service.removeReservable(newReservable);
		assertFalse(response.isSuccess());
	}
	
	@After
	public void after() {
		newReservable = null;
		response = null;
		service = null;
	}
	

}
