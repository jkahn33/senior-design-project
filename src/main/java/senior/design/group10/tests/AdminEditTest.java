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
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class AdminEditTest {
	@Autowired
	private AdminService adminService;
	NewAdmin newAdmin;
	EditAdmin editAdmin;
	ResponseObject response;
	
	@Before
	public void before() {
		newAdmin = new NewAdmin("admin name", "99999", "password");
		editAdmin = new EditAdmin("99999", "99991", "new admin name", "new password");
		response = null;
	}
	@Test
	public void test() {
		response = adminService.createNewAdmin(newAdmin);
		assertTrue(response.isSuccess());
		
		response = adminService.editAdmin(editAdmin);
		assertTrue(response.isSuccess());
		
		assertTrue(adminService.getAdminById("99999") == null);
	}
	@After
	public void after() {
		adminService = null;
		newAdmin = null;
		editAdmin = null;
		response = null;
	}
}
