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
	NewAdmin newAdmin;
	EditAdmin editAdmin;
	ResponseObject response;
	
	@Before
	public void before() {
		newAdmin = new NewAdmin("test name", "12345", "password");
		editAdmin = new EditAdmin("12345", "88888", "changed name", "newpass");
		response = null;
	}
	@Test
	public void test() {
		response = adminService.createNewAdmin(newAdmin);
		//This request should succeed.
		//assertTrue(response.isSuccess());
		
		response = adminService.editAdmin(editAdmin);
		//This request should succeed.
		assertTrue(response.isSuccess());
		System.out.println(response.getMessage());
	}
	@After
	public void after() {
		newAdmin = null;
		editAdmin = null;
		response = null;
	}
}
