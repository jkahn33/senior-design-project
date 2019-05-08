package nuwc.userloginsystem.objects;

import java.sql.Timestamp;

public class Users
{
    private String name;
    private Timestamp creationDate;
    private String badgeId;
    private String depCode;

    public Users(){}

    public Users(String name, Timestamp creationDate, String fiveDigExt, String depCode)
    {
        this.name = name;
        this.creationDate = creationDate;
        this.badgeId = fiveDigExt;
        this.depCode = depCode;
    }

    public String getName() {
        return name;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public String getDepCode() {
        return depCode;
    }
}