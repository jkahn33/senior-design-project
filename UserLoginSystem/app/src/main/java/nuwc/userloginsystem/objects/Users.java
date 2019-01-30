package nuwc.userloginsystem.objects;

import java.sql.Timestamp;

public class Users
{
    private String name;
    private Timestamp creationDate;
    private String fiveDigExt;
    private String depCode;

    public Users(){}

    public Users(String name, Timestamp creationDate, String fiveDigExt, String depCode)
    {
        this.name = name;
        this.creationDate = creationDate;
        this.fiveDigExt = fiveDigExt;
        this.depCode = depCode;
    }

    public String getName() {
        return name;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public String getFiveDigExt() {
        return fiveDigExt;
    }

    public String getDepCode() {
        return depCode;
    }
}