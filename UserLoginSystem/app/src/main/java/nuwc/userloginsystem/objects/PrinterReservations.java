package nuwc.userloginsystem.objects;

import java.util.Date;

public class PrinterReservations {
    private String name;
    private String printer;
    private Date jobSchedule;
    private Date jobScheduleEnd;
    private String jobDescription;
    private int id;
    private String jobDuration;

    public PrinterReservations(){}

    public PrinterReservations(String name, int id){
        this.name = name;
        this.id = id;
    }

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

    public String getJobDescription(){
        return jobDescription;
    }

    public int getId(){
        return id;
    }

    public String getJobDuration(){
        return jobDuration;
    }
}
