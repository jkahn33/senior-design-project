package senior.design.group10.service;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.PrinterReservationDAO;
import senior.design.group10.dao.ReservablesDAO;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.equipment.*;
import senior.design.group10.objects.response.PrinterUsageResponse;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.user.Users;

/**
 * Service class to handle business logic for 3D printer reservations
 */
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
	 *  if both are valid, check if time slot for start and end time interferes
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
		jobScheduleEnd = new Timestamp(cal.getTime().getTime()); //Where end time finishes calculating

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

	/**
	 * Gets usage statistics of totalHours about printers
	 * @return A list of PrinterUsageResponse objects
	 */
	public List<PrinterUsageResponse> getPrinterUsage(){
		List<PrinterUsageResponse> printers = new ArrayList<>();
		//creating a String array to make it easier to loop through each printer
		String[] ids = {"A", "B", "C", "D", "E"};

		//used to format the hours in order to not get lots of decimals from division
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);

		//getting printer information for each printer
		//it will loop through the array of IDs and print plug the current id in for printer specific stats
		for(String id : ids) {
			PrinterUsageHours currHours = printerDAO.getUsageHoursById(id);
			if(currHours.getTotalHours() != null) {
				printers.add(new PrinterUsageResponse(currHours.getPrinterId(),
						//the amount of usage is returned in seconds, so the seconds have to be converted to hours
						df.format(Double.parseDouble(currHours.getTotalHours())/3600)));
			}
			else{
				printers.add(new PrinterUsageResponse(id, "0"));
			}
		}
		return printers;
	}

	/**
	 * Gets the amount each user has used the printers and the department they belong to
	 * @return A list of PrinterUsageUsers objects
	 */
	public List<PrinterUsageUsers> getUserUsage(){
		return printerDAO.getUserUsage();
	}

	public List<PrinterReservations> getPrinterReservations(){
		return printerDAO.findAll();
	}

	public List<PrinterReservations> getPrinterReservationId(String id){
		log.info("ID IS: " + id);
		Optional<Users> usersOptional = usersDAO.findById(id);
		if(usersOptional.isPresent()){
			return printerDAO.getAllByUser(usersOptional.get());
		}
		return null;
	}
}
