package senior.design.group10.objects.response;

public class ReturnAdmin {
    private String name;
    private String badgeId;

    public ReturnAdmin(){}

    public ReturnAdmin( String name, String badgeId)
    {
        this.name = name;
        this.badgeId = badgeId;
    }

    public String getName() {
        return name;
    }

    public String getBadgeId() {
        return badgeId;
    }
}
