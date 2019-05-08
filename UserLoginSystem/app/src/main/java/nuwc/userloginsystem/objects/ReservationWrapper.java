package nuwc.userloginsystem.objects;

import java.util.Date;

public class ReservationWrapper {
    private String name;
    private int id;
    private Date date;
    private String resType;

    public ReservationWrapper(String name, int id, Date date, String resType) {
        this.name = name;
        this.date = date;
        this.id = id;
        this.resType = resType;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public int getId(){
        return id;
    }

    public String getResType(){
        return resType;
    }
}
