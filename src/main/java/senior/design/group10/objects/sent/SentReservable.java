package senior.design.group10.objects.sent;

import senior.design.group10.objects.equipment.ReservableKey;

public class SentReservable 
{
	private ReservableKey reservableKey;
	
	public SentReservable(String type, String id)
	{
		this.reservableKey = new ReservableKey(type,id);
	}
	
	
	public ReservableKey getRerservableKey()
	{
		return this.reservableKey;
	}
}
