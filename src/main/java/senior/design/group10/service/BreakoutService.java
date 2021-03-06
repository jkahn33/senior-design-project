package senior.design.group10.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.BreakoutReservationDAO;
import senior.design.group10.dao.PrinterReservationDAO;
import senior.design.group10.dao.ReservablesDAO;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.equipment.ReservableKey;
import senior.design.group10.objects.equipment.Reservables;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentBreakoutReservation;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.user.Users;

/**
 * Service class to handle business logic for breakout room reservations
 */
@Service
public class BreakoutService
{
	private final BreakoutReservationDAO breakoutDAO;
	private final UsersDAO usersDAO;
	private final ReservablesDAO reservablesDAO;
	@Autowired
	public BreakoutService( UsersDAO usersDAO, BreakoutReservationDAO breakoutDAO, ReservablesDAO reservablesDAO)
	{
		this.usersDAO = usersDAO;
		this.breakoutDAO = breakoutDAO;
		this.reservablesDAO = reservablesDAO;
	}

	/**
	 * Adding a printer reservation based on the user ext, time and duration
	 * This function checks for valid user ext and valid printer ID
	 *  if both are valid, check if time slot for start and end time interfer
	 *  with other print jobs
	 *  if interferes send message that time slot unavailable
	 *  If  time slot valid, update db to reflect reservation with
	 *  Start time, end time, user ext, etc.
	 *  
	 */
	//Check that all the rooms are available for reservation from the list of breakoutroom ids
	public ResponseObject checkRoomsAvailable(SentBreakoutReservation breakout)
	{
		boolean roomsAvailable[] = roomAvailable(breakout);
		String roomString = "";
		
		//Check that all rooms are available
		for(int x = 0; x < breakout.getReservableIdList().size();x++)
		{
			//if one room is unavailable send the list of availability and return false
			if(!roomsAvailable[x])
			{
				//Print the list of available and unavailble room
				for(int y = 0; y < breakout.getReservableIdList().size();y++)
				{
					roomString += "Room " + breakout.getReservableIdList().get(y) + " is ";
					if (roomsAvailable[y])
						roomString += "Available| ";
					else
						roomString += "Unavailable| ";
				}
				return new ResponseObject(false, roomString);
			}
		}
		//All room are availble then return true;
		return new ResponseObject(true,null);
	}
	
	public ResponseObject addBreakRes(SentBreakoutReservation breakout)
	{
		//Checking for user existance

		Optional<Users> usersOptional = usersDAO.findById(breakout.getUserExt());
		

		if(!usersOptional.isPresent())
		{
			return new ResponseObject(false, "User with extension " + breakout.getUserExt() + " cannot be found");
		}

		//Saving the breakout res to the db
		Users user = usersOptional.get();
		
		boolean roomsAvailable[] = roomAvailable(breakout);
		String roomString = "";
		
		for(int x = 0; x < breakout.getReservableIdList().size();x++)
		{
			if(!roomsAvailable[x])
			{
				roomString += breakout.getReservableIdList().get(x)+ " ";
			}
			else
			{
				Reservables reservable = new Reservables(new ReservableKey(breakout.getReservableType(),breakout.getReservableIdList().get(x)));
				
				BreakoutReservations newReservation = new BreakoutReservations(user,reservable,breakout.getResDescription(), Timestamp.valueOf(breakout.getResStart()), Timestamp.valueOf(breakout.getResEnd()),breakout.getNumPeople(),breakout.getAdditionalCom());
				breakoutDAO.save(newReservation);
			}
		}

		if (roomString == "")
			roomString = "All rooms succesfully reserved";
		else
			roomString += " Unavailable";
		
		return new ResponseObject(true,roomString);
	}
	
	//checks if the rooms are available
	//the code can be found in the  function above so use that to fill it out to then use in the check available function
	public boolean[] roomAvailable(SentBreakoutReservation breakout)
	{
		boolean roomList []= new boolean [breakout.getReservableIdList().size()];
		
		for (int resListNum = 0; resListNum < breakout.getReservableIdList().size();resListNum++)
		{
			//check what lists start with
			//Check to assure that the printer timeslots are available
			Optional <Reservables> reservableOptional = reservablesDAO.findById(new ReservableKey(breakout.getReservableType(),breakout.getReservableIdList().get(resListNum)));
			List<BreakoutReservations> checking = breakoutDAO.checkTimeAvailable(Timestamp.valueOf(breakout.getResStart()),Timestamp.valueOf(breakout.getResEnd()),breakout.getReservableIdList().get(resListNum));
			Optional <BreakoutReservations> precedingRes = breakoutDAO.checkNestedReservation(Timestamp.valueOf(breakout.getResStart()),breakout.getReservableIdList().get(resListNum));


			if(!reservableOptional.isPresent())
			{
				//checking for valid reservable id
				roomList[resListNum] = false;
			}
			
			else if(!checking.isEmpty())
			{
				//Checking for conflicting reservations
				roomList[resListNum] = false;
			}
			else if(precedingRes.isPresent() && (precedingRes.get().getResScheduleEnd().after(Timestamp.valueOf(breakout.getResStart()))))
			{
				//Checking for nested reservations
					roomList[resListNum] = false;
					
			}
			//check time available only checks if the times are within the selected timeslot
			//now try to find a way to find if the select time is not with another time
			else
				roomList[resListNum] = true;

		}
		//Iteratively check that the room is available
		return roomList;
	}
	
	public List<BreakoutReservations> todaysReservations()
	{
		//get all the reservations that have have same date as today
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		// getting tomorrows date from today    
		Calendar tomorrowDate = new GregorianCalendar();
		// reset hour, minutes, seconds and millis
		tomorrowDate.set(Calendar.HOUR_OF_DAY, 0);
		tomorrowDate.set(Calendar.MINUTE, 0);
		tomorrowDate.set(Calendar.SECOND, 0);
		tomorrowDate.set(Calendar.MILLISECOND, 0);
		// next day
		tomorrowDate.add(Calendar.DAY_OF_MONTH, 1);
		
		Timestamp ts = new Timestamp(tomorrowDate.getTimeInMillis());
		String tomorrow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);


		List<BreakoutReservations> todaysReservation = breakoutDAO.getCurrentBreakoutEvents(date, tomorrow);
		return todaysReservation;
	}
	
	public List<BreakoutReservations> getBreakoutReservations(){
		return breakoutDAO.findAll();
	}

	public List<BreakoutReservations> getBreakoutReservationId(String id){
		Optional<Users> usersOptional = usersDAO.findById(id);
		if(usersOptional.isPresent()){
			return breakoutDAO.findAllByUser(usersOptional.get());
		}
		return null;
	}

	public ResponseObject deleteById(String id){
		int theId = Integer.parseInt(id);
		Optional<BreakoutReservations> resOptional = breakoutDAO.findById(theId);
		if(resOptional.isPresent()){
			breakoutDAO.deleteById(theId);
			return new ResponseObject(true, null);
		}
		return new ResponseObject(false, "Cannot find breakout reservation.");
	}
}
