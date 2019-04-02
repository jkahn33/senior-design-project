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
import senior.design.group10.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class ActiveTest3 {
	@Autowired
	private UserService userService;
	ResponseObject response;
	List<ActiveUser> activeUserList;
	ActiveUser desiredActiveUser;
	
	@Before
	public void before() {
		response = null;
		desiredActiveUser = new ActiveUser("Test Last", "55555");
	}
	@Test
	public void test() {
		activeUserList = userService.getAllActiveUsers();
		//This request should succeed.
		assertTrue(activeUserList.contains(desiredActiveUser));
	}
	@After
	public void after() {
		response = null;
		desiredActiveUser = null;
	}
}
