package nuwc.userloginsystem.objects;

import java.util.List;

public class ActiveUser
{
    private String name;
    private String badgeID;

    public ActiveUser(){}

    public ActiveUser(String name, String badgeID)
    {
        this.name = name;
        this.badgeID = badgeID;
    }

    public String getName() {
        return name;
    }

    public String getBadgeID() {
        return badgeID;
    }
}
