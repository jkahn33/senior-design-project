package senior.design.group10.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.PrinterReservationDAO;
import senior.design.group10.dao.ReservablesDAO;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.equipment.ReservableKey;
import senior.design.group10.objects.equipment.Reservables;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.user.Users;

@Service
public class PrinterService 
{
	private final static Logger log = Logger.getLogger(PrinterService.class.getName());
	private final PrinterReservationDAO printerDAO;
	private final UsersDAO usersDAO;
	private final ReservablesDAO reservablesDAO;
	@Autowired
	public PrinterService( UsersDAO usersDAO, PrinterReservationDAO printerDAO, ReservablesDAO reservablesDAO)
	{
		this.usersDAO = usersDAO;
		this.printerDAO = printerDAO;
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
	public ResponseObject addPrintRes(SentPrinterReservation printer)
	{
		//Checking for user existance
		Optional<Users> usersOptional = usersDAO.findById(printer.getUserExt());
		Optional <Reservables> reservableOptional = reservablesDAO.findById(new ReservableKey(printer.getReservableType(),printer.getReservableId()));

		if(!usersOptional.isPresent()){
			return new ResponseObject(false, "User with extension " + printer.getUserExt() + " cannot be found");
		}
		if(!reservableOptional.isPresent()){
			return new ResponseObject(false, "Reservable with type " + printer.getReservableType() + printer.getReservableId() + " cannot be found");
		}
		

		//Calculate the end time

		Timestamp jobScheduleEnd = Timestamp.valueOf(printer.getJobSchedule());

		//Splitting the time by : for hours and mins
		String hourMin [] = printer.getJobDuration().split(":");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(jobScheduleEnd.getTime());
		// add number of hours
		cal.add(Calendar.MINUTE, Integer.parseInt(hourMin[1]) );
		cal.add(Calendar.HOUR, Integer.parseInt(hourMin[0]) );
		jobScheduleEnd = new Timestamp(cal.getTime().getTime());
		//Where end time finishes calculating




		//Check to assure that the printer/ timeslot are available
		java.util.List<PrinterReservations> checking = printerDAO.checkTimeAvailable(Timestamp.valueOf(printer.getJobSchedule()),jobScheduleEnd,printer.getReservableId());

		Optional <PrinterReservations> precedingRes = printerDAO.checkNestedReservation(Timestamp.valueOf(printer.getJobSchedule()),printer.getReservableId());


		if(precedingRes.isPresent())
		{
			if(precedingRes.get().getJobScheduleEnd().after(Timestamp.valueOf(printer.getJobSchedule())))
			{
				return new ResponseObject(false, "1 Conflicting times, timeslot "+ precedingRes.get().getJobSchedule()+ " to "+ precedingRes.get().getJobScheduleEnd() + " being used by Printer");

			}	
		}
		//check time available only checks if the times are within the selected timeslot
		//now try to find a way to find if the select time is not with another time
		if(!checking.isEmpty())
		{
			return new ResponseObject(false, "Conflicting times, timeslot "+ checking.get(0).getJobSchedule()+ " to "+ checking.get(0).getJobScheduleEnd() + " being used");
		}



		//Saving the printjob to the db
		Users user = usersOptional.get();
		Reservables reservable = reservableOptional.get();

		PrinterReservations newReservation = new PrinterReservations(user,reservable,printer.getJobDescription(),printer.getJobDuration(), Timestamp.valueOf(printer.getJobSchedule()), jobScheduleEnd,printer.getAdditionalCom());

		printerDAO.save(newReservation);

		return new ResponseObject(true,null);
	}

}
