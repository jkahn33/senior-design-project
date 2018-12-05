package senior.design.group10.objects.sent;

import java.sql.Timestamp;

public class SentBreakoutReservation 
{
	private String userExt;
	private String reservableType;
	private String reservableId;
	private String resDescription;
	private String resStart;
	private String resEnd;
	private String numPeople;
	private String additionalCom;

	public SentBreakoutReservation(){}
	
	public SentBreakoutReservation(String userExt, String reservableType, String reservableId, String resDescription, String resStart, String resEnd,String numPeople, String additionalCom)
	{
		this.userExt = userExt;
		this.reservableId = reservableId;
		this.reservableType = reservableType;
		this.resDescription= resDescription;
		this.resStart = resStart;
		this.resEnd = resEnd;
		this.numPeople = numPeople;
		this.additionalCom=additionalCom;
	}

	public String getUserExt() {
		return userExt;
	}


	public String getResDescription() {
		return resDescription;
	}

	public String getResStart() {
		return resStart;
	}

	public String getResEnd() {
		return resEnd;
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
