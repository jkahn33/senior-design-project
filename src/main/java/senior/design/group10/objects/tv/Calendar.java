package senior.design.group10.objects.tv;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "calendar")
public class Calendar 
{
	@Id
	@Column(name="cal_id")
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private int id;
	
	@Column
	private String eventName;
	@Column
	private Timestamp eventDate;
	@Column
	private String adminID;

	public Calendar(){}
	
	public Calendar( String eventName, Timestamp eventDate, String adminID)
	{
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.adminID = adminID;
	}
}
