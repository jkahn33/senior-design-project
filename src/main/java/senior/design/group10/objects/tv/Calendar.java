package senior.design.group10.objects.tv;

import org.hibernate.annotations.GenericGenerator;

import senior.design.group10.objects.user.Admin;

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
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Admin admin;

	public Calendar(){}
	
	public Calendar(String eventName, Timestamp eventDate, Admin admin)
	{
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.admin = admin;
	}
	
	public int getCal_ID() {
		return id;
	}

	public String getEventName() {
		return eventName;
	}

	public Timestamp getEventDate() {
		return eventDate;
	}
	
	public Admin getAdmin() {
		return admin;
	}
}
