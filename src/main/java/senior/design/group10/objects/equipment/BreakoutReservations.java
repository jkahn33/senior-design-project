package senior.design.group10.objects.equipment;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import senior.design.group10.objects.user.Users;

@Entity
@Table( name = "breakout_res")
public class BreakoutReservations 
{
	@Id
	@Column(name="res_id")
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private int id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_ext")
	private Users user;
	//@MapsId("reserable")
	@ManyToOne
	//@JoinColumn(name = "reservable")
	private Reservables reservable;
	@Column
	private String resDescription;
//	@Column
//	private String resDuration;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date resSchedule;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date resScheduleEnd;
	@Column
	private String numPeople;
	@Column
	private String additionalComments;
	
	public BreakoutReservations(){

	}

	public BreakoutReservations(Users user, Reservables reservable, String resDescription, Timestamp resSchedule, Timestamp resScheduleEnd, String numPeople, String additionalComments) {
		this.user = user;
		this.reservable = reservable;
		this.resDescription = resDescription;
		this.resSchedule = resSchedule;
		this.resScheduleEnd = resScheduleEnd;
		this.numPeople = numPeople;
		this.additionalComments = additionalComments;
	}
	
	public String getresDescription()
	{
		return this.resDescription;
	}
	
	public String getRoom()
	{
		return this.reservable.getRoom();
	}
	public Date getResSchedule()
	{
		return this.resSchedule;
	}
	
	public Date getResScheduleEnd()
	{
		return this.resScheduleEnd;
	}
	
	public String getEndHours()
	{
		String endHours  = ""+resScheduleEnd.getHours();
		return endHours;
	}
	public String getStartHours()
	{
		String startHours  = ""+resSchedule.getHours();
		return startHours;
	}
}
