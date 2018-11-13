package senior.design.group10.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.AdminDAO;
import senior.design.group10.dao.EquipmentCheckoutDAO;
import senior.design.group10.dao.EquipmentDAO;
import senior.design.group10.dao.PrinterReservationDAO;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.user.Users;

@Service
public class PrinterService 
{
	private final PrinterReservationDAO printerDAO;
	private final UsersDAO usersDAO;
	@Autowired
	public PrinterService( UsersDAO usersDAO, PrinterReservationDAO printerDAO)
	{
		this.usersDAO = usersDAO;
		this.printerDAO = printerDAO;
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
        if(!usersOptional.isPresent()){
            return new ResponseObject(false, "User with extension " + printer.getUserExt() + " cannot be found");
        }
        
        
        //Calculate the end time
        
        Timestamp jobScheduleEnd = printer.getJobSchedule();
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
        java.util.List<PrinterReservations> checking = printerDAO.checkTimeAvailable(printer.getJobSchedule(),jobScheduleEnd);

        if(!checking.isEmpty())
        {
        		return new ResponseObject(false, "Conflicting times, timeslot "+ checking.get(1).getJobSchedule()+ " to "+ checking.get(1).getJobScheduleEnd() + " being used");
        }
        
        //Saving the printjob to the db
        Users user = usersOptional.get();
        
        PrinterReservations newReservation = new PrinterReservations(user,printer.getJobDescription(),printer.getJobDuration(), printer.getJobSchedule(), jobScheduleEnd,printer.getAdditionalCom(),printer.getPrinterID());
        
        printerDAO.save(newReservation);

		return new ResponseObject(true,null);
	}

}
