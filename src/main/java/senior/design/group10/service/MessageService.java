package senior.design.group10.service;

/*
 * Todo
 * Function to remove all messages less than current date
 * Function to create images based on messsages table
 * 
 */

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.MessageDAO;
import senior.design.group10.dao.AdminDAO;
import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentMessage;
import senior.design.group10.objects.tv.Messages;
import senior.design.group10.objects.tv.Pi;
import senior.design.group10.objects.user.Admin;

import javax.imageio.ImageIO;

@Service
public class MessageService {
	private final static Logger log = Logger.getLogger(MessageService.class.getName());
	private final MessageDAO messageDAO;
	private final AdminDAO adminDAO;

	@Autowired
	public MessageService(MessageDAO messageDAO, AdminDAO adminDAO) {
		this.messageDAO = messageDAO;
		this.adminDAO = adminDAO;
	}

	//Message includes end date to describe how long the message will last for
	public ResponseObject createNewMessage(SentMessage message){
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());

		//find the user attached to the five digit extension
		Optional<Admin> adminOptional = adminDAO.findById(message.getAdminID());
		//checking if the five digit extension is valid
		if(!adminOptional.isPresent())
			return new ResponseObject(false, "Admin with ID " + message.getAdminID() + " cannot be found.");

		Messages newMessage = new Messages(message.getMessage(), Timestamp.valueOf(message.getEndDate()), adminOptional.get());
		log.info("admin ID: " + message.getAdminID() + ", time: " + currentTime);
		messageDAO.save(newMessage);

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
	//To do
	//Look at the BreakoutService and return the list of messages for dates greater than or equal to todays date
	//Implement the DAO function to return the messages then use it  in this class
	//Returns a list of the message which the dates are greater than or equal to current date

	public List <Messages> getCurrentMessages(){


		//Remove the messages less than today
		deletePastMessages();
		//Return everything else
		List <Messages> messageList = new ArrayList<Messages>();

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
}
