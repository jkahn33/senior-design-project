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
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class ActiveTest1 {
	@Autowired
	private UserService userService;
	SentUser newUserA, newUserB;
	ResponseObject response;
	List<ActiveUser> activeUsersList;
	List<Users> permUsersList;
	
	@Before
	public void before() {
		newUserA = new SentUser("user A", "00001", "dept");
		newUserB = new SentUser("user B", "00002", "dept");
		response = null;
	}
	@Test
	public void test() {
		response = userService.saveNewUser(newUserA);
		response = userService.saveNewUser(newUserB);
		//This request should succeed.
		assertTrue(response.isSuccess());
		activeUsersList = userService.getAllActiveUsers();
		permUsersList = userService.getAllUsers();
		//make sure that there are 2 accounts in each table
		assertTrue(activeUsersList.size() == 2);
		assertTrue(permUsersList.size() == 2);
	}
	@After
	public void after() {
		newUserA = null;
		newUserB = null;
		activeUsersList = null;
		permUsersList = null;
		response = null;
	}
}
