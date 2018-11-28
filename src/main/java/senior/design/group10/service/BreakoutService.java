package senior.design.group10.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

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
	public ResponseObject addBreakRes(SentBreakoutReservation breakout)
	{
		//Checking for user existance
		Optional<Users> usersOptional = usersDAO.findById(breakout.getUserExt());
		Optional <Reservables> reservableOptional = reservablesDAO.findById(new ReservableKey(breakout.getReservableType(),breakout.getReservableId()));

		if(!usersOptional.isPresent()){
			return new ResponseObject(false, "User with extension " + breakout.getUserExt() + " cannot be found");
		}
		if(!reservableOptional.isPresent()){
			return new ResponseObject(false, "Reservable with type " + breakout.getReservableType() + breakout.getReservableId() + " cannot be found");
		}
		

		//Calculate the end time

		Timestamp jobScheduleEnd = breakout.getResSchedule();
		//Splitting the time by : for hours and mins
		String hourMin [] = breakout.getResDuration().split(":");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(jobScheduleEnd.getTime());
		// add number of hours
		cal.add(Calendar.MINUTE, Integer.parseInt(hourMin[1]) );
		cal.add(Calendar.HOUR, Integer.parseInt(hourMin[0]) );
		jobScheduleEnd = new Timestamp(cal.getTime().getTime());
		//Where end time finishes calculating




		//Check to assure that the printer/ timeslot are available
		java.util.List<BreakoutReservations> checking = breakoutDAO.checkTimeAvailable(breakout.getResSchedule(),jobScheduleEnd,breakout.getReservableId());

		Optional <BreakoutReservations> precedingRes = breakoutDAO.checkNestedReservation(breakout.getResSchedule(),breakout.getReservableId());


		if(precedingRes.isPresent())
		{
			if(precedingRes.get().getResScheduleEnd().after(breakout.getResSchedule()))
			{
				return new ResponseObject(false, "1 Conflicting times, timeslot "+ precedingRes.get().getResSchedule()+ " to "+ precedingRes.get().getResScheduleEnd() + " being used by Printer");

			}	
		}
		//check time available only checks if the times are within the selected timeslot
		//now try to find a way to find if the select time is not with another time
		if(!checking.isEmpty())
		{
			return new ResponseObject(false, "Conflicting times, timeslot "+ checking.get(0).getResSchedule()+ " to "+ checking.get(0).getResScheduleEnd() + " being used");
		}



		//Saving the printjob to the db
		Users user = usersOptional.get();
		Reservables reservable = reservableOptional.get();

		BreakoutReservations newReservation = new BreakoutReservations(user,reservable,breakout.getResDescription(),breakout.getResDuration(), breakout.getResSchedule(), jobScheduleEnd,breakout.getNumPeople(),breakout.getAdditionalCom());

		
		breakoutDAO.save(newReservation);

		return new ResponseObject(true,null);
	}
}
