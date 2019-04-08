package senior.design.group10.objects.sent;

public class SentFuture 
{
	private String future;
	private String startDate;
    private String endDate;
    private String adminID;

    public SentFuture() {}
    
    public SentFuture(String future, String adminID, String startDate, String endDate){
        this.future = future;
        this.endDate = endDate;
        this.adminID = adminID;
        this.startDate = startDate;
    }

    public String getFuture() {
        return future;
    }

    public String getAdminID() {
        return adminID;
    }
    
    public String getStartDate()
    {
    		return startDate;
    }

	public String getEndDate() {
		return endDate;
	}
}
