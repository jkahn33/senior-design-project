package senior.design.group10.objects.equipment;

import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reservables")
public class Reservables 
{
	@EmbeddedId
	private ReservableKey id;
	

	public Reservables()
	{
	}
	
	
	public Reservables(ReservableKey id) 
	{
		this.id = id;
	}
}
