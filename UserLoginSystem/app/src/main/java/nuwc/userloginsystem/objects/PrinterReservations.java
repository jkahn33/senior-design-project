package nuwc.userloginsystem.objects;

import java.util.Date;

public class PrinterReservations {
    private String name;
    private String printer;
    private Date jobSchedule;
    private Date jobScheduleEnd;

    public PrinterReservations(){}

    public String getName() {
        return name;
    }

    public String getPrinter() {
        return printer;
    }

    public Date getJobSchedule() {
        return jobSchedule;
    }

    public Date getJobScheduleEnd() {
        return jobScheduleEnd;
    }
}