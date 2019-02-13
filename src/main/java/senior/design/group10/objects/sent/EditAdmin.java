package senior.design.group10.objects.sent;

public class EditAdmin {
    private String oldBadgeID;
    private String badgeID;
    private String name;
    private String newPass;

    public EditAdmin(){}

    public EditAdmin(String oldBadgeID, String badgeID, String name, String newPass) {
        this.oldBadgeID = oldBadgeID;
        this.badgeID = badgeID;
        this.name = name;
        this.newPass = newPass;
    }

    public String getOldBadgeID(){
        return oldBadgeID;
    }

    public String getBadgeID() {
        return badgeID;
    }

    public String getName() {
        return name;
    }

    public String getNewPass() {
        return newPass;
    }
}
