package senior.design.group10.objects.sent;

public class SentUser 
{
    private String name;
    private String badgeID;
    private String dep;

    public SentUser(){}

    public SentUser(String name, String badgeID, String dep){
        this.name = name;
        this.badgeID = badgeID;
        this.dep = dep;
    }

    public String getName() {
        return name;
    }

    public String getBadgeID() {
        return badgeID;
    }

    public String getDep() {
        return dep;
    }
}
