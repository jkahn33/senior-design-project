package senior.design.group10.objects.equipment;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import senior.design.group10.objects.user.Users;

import static javax.persistence.CascadeType.ALL;

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

	public String getName(){
		return user.getName();
	}

	public String getRoom()
	{
		return reservable.getRoom();
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
		return ""+resScheduleEnd.getHours();
	}
	public String getStartHours()
	{
		return ""+resSchedule.getHours();
	}
}
