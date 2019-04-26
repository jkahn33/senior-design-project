package senior.design.group10.service;

/*
 * Todo
 * Function to remove all messages less than current date
 * Function to create images based on messsages table
 * 
 */

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.MessageDAO;
import senior.design.group10.dao.AdminDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentMessage;
import senior.design.group10.objects.tv.Messages;
import senior.design.group10.objects.user.Admin;

@Service
public class MessageService {
	private final static Logger log = Logger.getLogger(MessageService.class.getName());
	private final MessageDAO messageDAO;
	private final AdminDAO adminDAO;
	private final PiService piService;
	private final BreakoutService breakoutService;
	private final FutureService futureService;

	@Autowired
	public MessageService(MessageDAO messageDAO, AdminDAO adminDAO, PiService piService, BreakoutService breakoutService, FutureService futureService) {
		this.messageDAO = messageDAO;
		this.adminDAO = adminDAO;
		this.piService = piService;
		this.futureService = futureService;
		this.breakoutService = breakoutService;
	}

	//Message includes end date to describe how long the message will last for
	public ResponseObject createNewMessage(SentMessage message){
		//uses the Calendar API to add days to today's current date
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, message.getDuration());
		Date date = calendar.getTime();

		Timestamp endTime = new Timestamp(date.getTime());

		//find the user attached to the five digit extension
		Optional<Admin> adminOptional = adminDAO.findById(message.getAdminID());
		//checking if the five digit extension is valid
		if(!adminOptional.isPresent())
			return new ResponseObject(false, "Admin with ID " + message.getAdminID() + " cannot be found.");

		Messages newMessage = new Messages(message.getMessage(), endTime, adminOptional.get());
		messageDAO.save(newMessage);

		piService.renderMessagesImage(getCurrentMessages());

		piService.piListFill();
		piService.copyFolderToPi("PiImages", "Pictures/Slides");

		return new ResponseObject(true, adminOptional.get().getName());
	}

	public void deletePastMessages()
	{
		//Remove the message that end dates are less than current time
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

		//Timestamp currentTime = new Timestamp(date.getTime());
		messageDAO.deletPastMessages(date);
		//System.out.println(currentTime);

	}
	
	public void deleteMessagesById(int [] ids)
	{
		//for every int in id delete by id
		for(int x = 0; x < ids.length; x++)
			messageDAO.deleteById(ids[x]);
	}
	

	public List <Messages> getCurrentMessages(){


		//Remove the messages less than today
		deletePastMessages();
		//Return everything else
		List <Messages> messageList = new ArrayList<>();

		Iterable <Messages> messagesIt = messageDAO.findAll();


		//get the breakout reservations for today
		for (Messages message : messagesIt)
			messageList.add(message);
		// Or like this...
		/*for(int i = 0; i < messageList.size(); i++) {
			System.out.println(messageList.get(i).getMessage());
		}*/
		return messageList;

	}
	
	/**
	 * Gets a list of all of the messages in the database.
	 * @return list of all messages.
	 */
    public List<Messages> getAllMessages() {
    	return (List<Messages>)messageDAO.findAll();
    }

}
