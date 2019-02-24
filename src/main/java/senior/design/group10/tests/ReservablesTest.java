package senior.design.group10.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import senior.design.group10.application.Application;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.ReturnAdmin;
import senior.design.group10.objects.sent.EditAdmin;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.objects.sent.SentEquipment;
import senior.design.group10.objects.sent.SentReservable;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.EquipmentService;
import senior.design.group10.service.ReservablesService;
import senior.design.group10.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class ReservablesTest {
	@Autowired
	private ReservablesService reservablesService;
	@Autowired
	private EquipmentService equipmentService;
	SentReservable r1, r2, r3, r4, r5;
	Equipment e;
	SentEquipment se;
	ResponseObject response;
	
	@Before
	public void before() {
		r1 = new SentReservable("Printer", "A");
		r2 = new SentReservable("Breakout", "A");
		r3 = new SentReservable("Breakout", "B");
		r4 = new SentReservable("Breakout", "C");
		r5 = new SentReservable("Breakout", "D");
		e = new Equipment("00000", "Test Equipment", true);
		se = new SentEquipment("00000", "00001", "00001");
	}
	@Test
	public void test() {
		/*
		response = reservablesService.saveNewReservable(r1);
		assertTrue(response.isSuccess());
		response = reservablesService.saveNewReservable(r2);
		assertTrue(response.isSuccess());
		*/
		response = reservablesService.saveNewReservable(r3);
		response = reservablesService.saveNewReservable(r4);
		response = reservablesService.saveNewReservable(r5);
		// response = equipmentService.addNewEquipment(e);
		//assertTrue(response.isSuccess());
		//response = equipmentService.checkout(se);
		//assertTrue(response.isSuccess());
	}
	@After
	public void after() {
		reservablesService = null;
		r1 = null;
		e = null;
		se = null;
		response = null;
	}
}
