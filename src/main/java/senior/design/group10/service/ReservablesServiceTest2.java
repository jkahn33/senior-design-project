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
public class ReservablesServiceTest2 {
	@Autowired
	private ReservablesService service;
	private SentReservable erroneousReservable;
	private ResponseObject response;
	
	@Before
	public void before() {
		erroneousReservable = new SentReservable("dummy type", "1");
	}
	@Test
	public void test() {
		//Adding an erroneous reservable to the table. Expected to fail.
		response = service.saveNewReservable(erroneousReservable);
		assertFalse(response.isSuccess());
	}
	
	@After
	public void after() {
		erroneousReservable = null;
		response = null;
		service = null;
	}
	

}
