package senior.design.group10.tests;

import static org.junit.Assert.*;

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
import senior.design.group10.objects.sent.EditAdmin;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.PrinterService;
import senior.design.group10.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class PrinterServiceTest {
	@Autowired
	private PrinterService printerService;
	private ResponseObject response;
	private SentPrinterReservation res;
	
	@Before
	public void before() {
		res = new SentPrinterReservation("00001", "Printer", "A", "desc", "2007-6-11 10:10:10.0", "2:00", "comment");
	}
	@Test
	public void test() {
		response = printerService.addPrintRes(res);
		assertTrue(response.isSuccess());
	}
	@After
	public void after() {
		printerService = null;
		res = null;
		response = null;
	}
}
