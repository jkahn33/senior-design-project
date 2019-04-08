package senior.design.group10.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.AdminDAO;
import senior.design.group10.dao.FutureDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentFuture;
import senior.design.group10.objects.tv.Future;
import senior.design.group10.objects.user.Admin;
@Service
public class FutureService 
{
	private final FutureDAO futureDAO;
	private final AdminDAO adminDAO;

	@Autowired
	public FutureService(FutureDAO futureDAO, AdminDAO adminDAO) {
		this.futureDAO = futureDAO;
		this.adminDAO = adminDAO;
	}

	//Message includes end date to describe how long the message will last for
	public ResponseObject createNewFuture(SentFuture future){
		//Date date = new Date();
		//Timestamp currentTime = new Timestamp(date.getTime());

		//find the user attached to the five digit extension
		Optional<Admin> adminOptional = adminDAO.findById(future.getAdminID());
		//checking if the five digit extension is valid
		if(!adminOptional.isPresent())
			return new ResponseObject(false, "Admin with ID " + future.getAdminID() + " cannot be found.");

		Future newFuture = new Future(future.getFuture(), Timestamp.valueOf(future.getEndDate()), Timestamp.valueOf(future.getStartDate()), adminOptional.get());
		//log.info("admin ID: " + future.getAdminID() + ", time: " + currentTime);
		futureDAO.save(newFuture);

		return new ResponseObject(true, adminOptional.get().getName());
	}

	public void deletePastFuture()
	{
		//Remove the message that end dates are less than current time
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

		//Timestamp currentTime = new Timestamp(date.getTime());
		futureDAO.deletPastFuture(date);
		//System.out.println(currentTime);

	}
	//To do
	//Look at the BreakoutService and return the list of messages for dates greater than or equal to todays date
	//Implement the DAO function to return the messages then use it  in this class
	//Returns a list of the message which the dates are greater than or equal to current date

	public List <Future> getFutureMessages(){


		//Remove the messages less than today
		deletePastFuture();
		//Return everything else
		List <Future> futureList = new ArrayList<Future>();

		Iterable <Future> futureIt = futureDAO.findAll();


		//get the breakout reservations for today


		for (Future future : futureIt)
			futureList.add(future);
		// Or like this...
		/*for(int i = 0; i < messageList.size(); i++) {
				System.out.println(messageList.get(i).getMessage());
			}*/
		return futureList;

	}
}
