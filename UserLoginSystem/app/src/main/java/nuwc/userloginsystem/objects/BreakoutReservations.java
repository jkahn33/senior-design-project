package nuwc.userloginsystem.objects;

import java.util.Date;


public class BreakoutReservations {
    private String name;
    private String room;
    private Date resSchedule;
    private Date resScheduleEnd;
    private String startHours;
    private String endHours;
    private String resDescription;

    public BreakoutReservations(){}

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public Date getResSchedule() {
        return resSchedule;
    }

    public Date getResScheduleEnd() {
        return resScheduleEnd;
    }

    public String getStartHours() {
        return startHours;
    }

    public String getEndHours() {
        return endHours;
    }

    public String getResDescription() {
        return resDescription;
    }
}
