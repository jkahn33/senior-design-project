package senior.design.group10.objects.sent;

public class NewAdmin {
    private String name;
    private String badgeID;
    private String password;

    public NewAdmin(){}

    public NewAdmin(String name, String badgeID, String password) {
        this.name = name;
        this.badgeID = badgeID;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getBadgeID() {
        return badgeID;
    }

    public String getPassword() {
        return password;
    }
}
