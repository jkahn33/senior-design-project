package senior.design.group10.objects.sent;

import java.sql.Timestamp;

public class SentPrinterReservation
{
	private String badgeID;
	private String reservableType;
	private String reservableId;
	private String jobDescription;
	//must be sent as a string because sent JSON string cannot be deserialized
	private String jobSchedule;
	private String jobDuration;
	private String additionalCom;

	public SentPrinterReservation(){}
	
	public SentPrinterReservation(String badgeID, String reservableType, String reservableId, String jobDescription, String jobSchedule, String jobDuration, String additionalCom)
	{
		this.badgeID = badgeID;
		this.reservableId = reservableId;
		this.reservableType = reservableType;
		this.jobDescription=jobDescription;
		this.jobSchedule=jobSchedule;
		this.jobDuration=jobDuration;
		this.additionalCom=additionalCom;
	}

	public String getBadgeID() {
		return badgeID;
	}


	public String getJobDescription() {
		return jobDescription;
	}



	public String getJobSchedule() {
		return jobSchedule;
	}


	public String getJobDuration() {
		return jobDuration;
	}


	public String getAdditionalCom() {
		return additionalCom;
	}


	public String getReservableType() {
		return reservableType;
	}


	public String getReservableId() {
		return reservableId;
	}

	
}
