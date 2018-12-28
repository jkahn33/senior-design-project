package senior.design.group10.objects.sent;

public class SentMessage {
    private String message;
    private String adminID;

    public SentMessage() {}
    
    public SentMessage(String message, String adminID){
        this.message = message;
        this.adminID = adminID;
    }

    public String getMessage() {
        return message;
    }

    public String getAdminID() {
        return adminID;
    }

}
