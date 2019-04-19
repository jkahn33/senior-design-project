package senior.design.group10.objects.response;

public class ReturnAdmin {
    private String name;
    private String badgeID;

    public ReturnAdmin(){}

    public ReturnAdmin( String name, String badgeID)
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
