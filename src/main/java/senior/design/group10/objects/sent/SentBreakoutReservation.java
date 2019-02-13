package senior.design.group10.objects.sent;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SentBreakoutReservation 
{
	private String badgeID;
	private String reservableType;
	private List <String> reservableIdList;
	private String resDescription;
	private String resStart;
	private String resEnd;
	private String numPeople;
	private String additionalCom;

	public SentBreakoutReservation(){}
	
	public SentBreakoutReservation(String badgeID, String reservableType, List <String> reservableIdList, String resDescription, String resStart, String resEnd,String numPeople, String additionalCom)
	{
		this.badgeID = badgeID;
		this.reservableIdList = new ArrayList<String>(reservableIdList);
		this.reservableType = reservableType;
		this.resDescription= resDescription;
		this.resStart = resStart;
		this.resEnd = resEnd;
		this.numPeople = numPeople;
		this.additionalCom=additionalCom;
	}

	public String getBadgeID() {
		return badgeID;
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


	public List <String> getReservableIdList() {
		return reservableIdList;
	}
}
