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
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class ActiveTest1 {
	@Autowired
	private UserService userService;
	SentUser newUser;
	ResponseObject response;
	
	@Before
	public void before() {
		newUser = new SentUser("Name", "badge", "dept");
		response = null;
	}
	@Test
	public void test() {
		response = userService.saveNewUser(newUser);
		//This request should succeed.
		assertTrue(response.isSuccess());
	}
	@After
	public void after() {
		newUser = null;
		response = null;
	}
}
