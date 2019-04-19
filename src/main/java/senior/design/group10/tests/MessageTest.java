package senior.design.group10.tests;



import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import senior.design.group10.application.Application;
import senior.design.group10.objects.sent.SentMessage;
import senior.design.group10.service.MessageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class MessageTest {
	@Autowired
	private MessageService messageService;
	private SentMessage sentMessage;
	
	@Test
	public void test() {
		sentMessage = new SentMessage("test message", "99991", "2007-6-11 10:10:00.0");
		messageService.createNewMessage(sentMessage);
	}
}
