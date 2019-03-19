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
		for(int i = 0; i < messageList.size(); i++) {
			System.out.println(messageList.get(i).getMessage());
		}
		return messageList;

	}
	/*
	 * From the messages received from get current message create the rendered image
	 */

	public boolean renderBreakoutImage(List<BreakoutReservations> breakoutList)
	{

		//Create a switch to choose header for image to display
		String headerText= "USWRIC Today's Breakout Events";
		
		//make size fit 1080p tv
		int width = 1920;
		int height = 1080;

		// Constructs a BufferedImage of one of the predefined image types.
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();
		//used to setup Java rendering algorithms
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		//render the gradient blue/yellow background
		GradientPaint paint = new GradientPaint(0, 0, Color.BLUE, 0, height, Color.ORANGE);
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, width, height);

		//render the header text
		Font font = new Font("Arial", Font.BOLD, 100);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int x = ((width - fm.stringWidth(headerText)) / 2);

		g2d.setColor(Color.BLACK);
		g2d.drawString(headerText, x, 150);

		//rendering of administrative messages
		x = 64;
		int y = 300;
		ArrayList<String> testArr = new ArrayList<>();
		
		//for every string in messages add to testArr
		for( int loop = 0; loop< breakoutList.size();loop++)
		{
			String message = breakoutList.get(loop).getresDescription() + " Room: " + breakoutList.get(loop).getRoom()
					+ ", From " + breakoutList.get(loop).getStartHours()+ " to "+ breakoutList.get(loop).getEndHours();
			testArr.add(message);
		}
		//testArr.add("Printer number 3 is broken. Please refrain from using printer number 3. Test1 test2 test3 test4. Test5 test6 test7 test8.");
		//testArr.add("The USWRIC will be closed to all visitors on Friday, February 22nd.");

		font = new Font("Arial", Font.BOLD, 48);
		g2d.setFont(font);

		g2d.setColor(Color.BLACK);

		for(String s : testArr) {
			String[] splits = s.split(" ");
			for(int i = 0; i < splits.length; i++){
				String stringToPrint = getStringToPrint(splits, i);
				String[] printString = stringToPrint.split("@#&");
				g2d.drawString(printString[0], x, y);
				y += 50;

				i = Integer.parseInt(printString[1]);
			}

			g2d.setStroke(new BasicStroke(10));
			g2d.draw(new Line2D.Float(210, y, 1710, y));
			y+=100;
		}

		// Disposes of this graphics context and releases any system resources that it is using.
		g2d.dispose();

		try {
			// Save as PNG
			File file = new File("PiImages/breakout_events.png");
			OutputStream out = new FileOutputStream(file);
			ImageIO.write(bufferedImage, "png", file);
		}
		catch (Exception e){
			log.severe(e.toString());
			return false;
		}
		return true;
	}
	 public boolean renderMessagesImage(List<Messages> messageList){
	        String headerText = "USWRIC Important Information";
	        //make size fit 1080p tv
	        int width = 1920;
	        int height = 1080;

	        // Constructs a BufferedImage of one of the predefined image types.
	        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	        // Create a graphics which can be used to draw into the buffered image
	        Graphics2D g2d = bufferedImage.createGraphics();
	        //used to setup Java rendering algorithms
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

	        //render the gradient blue/yellow background
	        GradientPaint paint = new GradientPaint(0, 0, Color.BLUE, 0, height, Color.ORANGE);
	        g2d.setPaint(paint);
	        g2d.fillRect(0, 0, width, height);

	        //render the header text
	        Font font = new Font("Arial", Font.BOLD, 100);
	        g2d.setFont(font);
	        FontMetrics fm = g2d.getFontMetrics();
	        int x = ((width - fm.stringWidth(headerText)) / 2);

	        g2d.setColor(Color.BLACK);
	        g2d.drawString(headerText, x, 150);

	        //rendering of administrative messages
	        x = 64;
	        int y = 300;
	        ArrayList<String> testArr = new ArrayList<>();
	        //for every string in messages add to testArr
	        for( int loop = 0; loop< messageList.size();loop++)
	        {
	        		testArr.add(messageList.get(loop).getMessage());
	        }
	        //testArr.add("Printer number 3 is broken. Please refrain from using printer number 3. Test1 test2 test3 test4. Test5 test6 test7 test8.");
	        //testArr.add("The USWRIC will be closed to all visitors on Friday, February 22nd.");

	        font = new Font("Arial", Font.BOLD, 48);
	        g2d.setFont(font);

	        g2d.setColor(Color.BLACK);

	        for(String s : testArr) {
	            String[] splits = s.split(" ");
	            for(int i = 0; i < splits.length; i++){
	                String stringToPrint = getStringToPrint(splits, i);
	                String[] printString = stringToPrint.split("@#&");
	                g2d.drawString(printString[0], x, y);
	                y += 50;

	                i = Integer.parseInt(printString[1]);
	            }

	            g2d.setStroke(new BasicStroke(10));
	            g2d.draw(new Line2D.Float(210, y, 1710, y));
	            y+=100;
	        }

	        // Disposes of this graphics context and releases any system resources that it is using.
	        g2d.dispose();

	        try {
	            // Save as PNG
	            File file = new File("PiImages/admin_messages.png");
	            OutputStream out = new FileOutputStream(file);
	            ImageIO.write(bufferedImage, "png", file);
	        }
	        catch (Exception e){
	            log.severe(e.toString());
	            return false;
	        }
	        return true;
	    }
	private String getStringToPrint(String[] splits, int index){
		StringBuilder newString = new StringBuilder();
		while(index < splits.length && newString.length() + splits[index].length() < 75){
			newString.append(" ");
			newString.append(splits[index]);
			index++;
		}
		index--;
		newString.append("@#&");
		newString.append(index);
		return newString.toString();
	}

	//Implement the events for todays breakout reservations
	//Will display breakout res description, room number and time
}
