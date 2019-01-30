package senior.design.group10.objects.sent;

import java.sql.Timestamp;

public class SentCalendar {
    private String eventName;
    private String eventDate;
    private String adminID;

    public SentCalendar() {}
    
    public SentCalendar(String eventName, String eventDate, String adminID){
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.adminID = adminID;
    }

    public String getName() {
        return eventName;
    }
    
    public String getDate() {
        return eventDate;
    }

    public String getAdminID() {
        return adminID;
    }
}
