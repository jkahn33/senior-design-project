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
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.ReturnAdmin;
import senior.design.group10.objects.sent.EditAdmin;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class ActiveTest2 {
	@Autowired
	private AdminService adminService;
	NewAdmin newAdminA, newAdminB;
	ReturnAdmin returnAdmin;
	ResponseObject response;
	
	@Before
	public void before() {
		newAdminA = new NewAdmin("test name A", "00001", "password");
		newAdminB = new NewAdmin("test name B", "00002", "password");
		returnAdmin = null;
	}
	@Test
	public void test() {
		adminService.createNewAdmin(newAdminA);
		adminService.createNewAdmin(newAdminB);
		
		returnAdmin = adminService.getAdminById(newAdminA.getBadgeID());
		assertTrue(returnAdmin != null);
		returnAdmin = adminService.getAdminById(newAdminB.getBadgeID());
		assertTrue(returnAdmin != null);
	}
	@After
	public void after() {
		newAdminA = null;
		newAdminB = null;
		returnAdmin = null;
		response = null;
	}
}
