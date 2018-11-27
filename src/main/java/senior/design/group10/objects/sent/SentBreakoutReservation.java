package senior.design.group10.objects.sent;

import java.sql.Timestamp;

public class SentBreakoutReservation 
{
	private String userExt;
	private String reservableType;
	private String reservableId;
	private String resDescription;
	private Timestamp resSchedule;
	private String resDuration;
	private String numPeople;
	private String additionalCom;
	
	public SentBreakoutReservation(String userExt, String reservableType, String reservableId, String resDescription, Timestamp resSchedule, String resDuration,String numPeople, String additionalCom)
	{
		this.userExt = userExt;
		this.reservableId = reservableId;
		this.reservableType = reservableType;
		this.resDescription= resDescription;
		this.resSchedule = resSchedule;
		this.resDuration = resDuration;
		this.numPeople = numPeople;
		this.additionalCom=additionalCom;
	}

	public String getUserExt() {
		return userExt;
	}


	public String getResDescription() {
		return resDescription;
	}



	public Timestamp getResSchedule() {
		return resSchedule;
	}


	public String getResDuration() {
		return resDuration;
	}

	public String getNumPeople() 
	{
		return numPeople;
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
