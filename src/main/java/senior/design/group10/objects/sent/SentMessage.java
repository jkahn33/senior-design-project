package senior.design.group10.objects.sent;


public class SentMessage {
    private String message;
    private Integer duration;
    private String adminID;

    public SentMessage() {}
    
    public SentMessage(String message, String adminID, Integer duration){
        this.message = message;
        this.duration = duration;
        this.adminID = adminID;
    }

    public String getMessage() {
        return message;
    }

    public String getAdminID() {
        return adminID;
    }

	public Integer getDuration() {
		return duration;
	}

}
