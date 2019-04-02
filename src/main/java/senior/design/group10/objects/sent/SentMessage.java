package senior.design.group10.objects.sent;


public class SentMessage {
    private String message;
    private String endDate;
    private String adminID;

    public SentMessage() {}
    
    public SentMessage(String message, String adminID, String endDate){
        this.message = message;
        this.endDate = endDate;
        this.adminID = adminID;
    }

    public String getMessage() {
        return message;
    }

    public String getAdminID() {
        return adminID;
    }

	public String getEndDate() {
		return endDate;
	}

}
