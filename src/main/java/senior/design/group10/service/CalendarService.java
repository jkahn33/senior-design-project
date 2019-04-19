package senior.design.group10.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.AdminDAO;
import senior.design.group10.dao.CalendarDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentCalendar;
import senior.design.group10.objects.tv.Calendar;
import senior.design.group10.objects.user.Admin;

@Service
public class CalendarService {
    private final CalendarDAO calendarDAO;
    private final AdminDAO adminDAO;

    @Autowired
    public CalendarService(CalendarDAO calendarDAO, AdminDAO adminDAO) {
        this.calendarDAO = calendarDAO;
        this.adminDAO = adminDAO;
    }

    public ResponseObject createNewEvent(SentCalendar calendar){
        //find the user attached to the five digit extension
        Optional<Admin> adminOptional = adminDAO.findById(calendar.getAdminID());
        //checking if the five digit extension is valid
        if(!adminOptional.isPresent())
        	return new ResponseObject(false, "Admin with ID " + calendar.getAdminID() + " cannot be found.");
        
        Calendar newCalendar = new Calendar(calendar.getName(), Timestamp.valueOf(calendar.getDate()), adminOptional.get());
        calendarDAO.save(newCalendar);
        
        return new ResponseObject(true, adminOptional.get().getName());
    }
    
    /*
     * Taking any timestamp within a given day, returns all events within that day.
     */
    public List<Calendar> getEventsByDate(Timestamp date) {
    	//JDBC timestamp escape format:
    	//	2007-6-11 10:10:00.0
    	//	YYYY-MM-DD HH:MM:SS.f...
    	//we convert date into a string and manipulate it using this format.
		String dateString = date.toString();
    	
		//locate the space character separating the date from the time.
		//we must do this manually because all dates are not the same length.
		char current = 0;
		int spaceIndex = 0;
		while(current != ' ') {
			current = dateString.charAt(spaceIndex);
			spaceIndex++;
		}
		
		//truncate the time from the string and add new times spanning the given date
		String truncString = dateString.substring(0, spaceIndex);
		String startString = truncString + "00:00:00.0";
		String endString = truncString + "23:59:59.9";
		
		return calendarDAO.getEventsByTime(Timestamp.valueOf(startString), Timestamp.valueOf(endString));
    }
    
    public List<Calendar> getAllEvents() {
    	return calendarDAO.findAll();
    }
}
