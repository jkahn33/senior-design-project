package senior.design.group10.objects.sent;

import java.sql.Timestamp;

public class SentPrinterReservation
{
	private String userExt;
	private String jobDescription;
	private Timestamp jobSchedule;
	private String jobDuration;
	private String additionalCom;
	private String printerID;
	
	public SentPrinterReservation(String userExt, String jobDescription, Timestamp jobSchedule, String jobDuration, String additionalCom, String printerID)
	{
		this.userExt = userExt;
		this.jobDescription=jobDescription;
		this.jobSchedule=jobSchedule;
		this.jobDuration=jobDuration;
		this.additionalCom=additionalCom;
		this.printerID=printerID;
	}

	public String getUserExt() {
		return userExt;
	}


	public String getJobDescription() {
		return jobDescription;
	}



	public Timestamp getJobSchedule() {
		return jobSchedule;
	}


	public String getJobDuration() {
		return jobDuration;
	}


	public String getAdditionalCom() {
		return additionalCom;
	}


	public String getPrinterID() {
		return printerID;
	}
	
}
