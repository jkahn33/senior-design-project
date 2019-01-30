package senior.design.group10.objects.equipment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReservableKey implements Serializable 
{
	@Column(name = "type", nullable = false, length=20)
	private String type;
	
	@Column(name = "id", nullable = false, length=20)
	private String id;
	
	public ReservableKey()
	{
		
	}
	
	public ReservableKey( String type, String id)
	{
		this.id = id;
		this.type = type;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public String getID()
	{
		return this.id;
	}
}
