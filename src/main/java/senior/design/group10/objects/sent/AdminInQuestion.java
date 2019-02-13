package senior.design.group10.objects.sent;

public class AdminInQuestion {
    private String badgeID;
    private String password;

    public AdminInQuestion(){}

    public AdminInQuestion(String badgeID, String password){
        this.badgeID = badgeID;
        this.password = password;
    }

    public String getBadgeID() {
        return badgeID;
    }

    public String getPassword() {
        return password;
    }
}
