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
	@JoinColumn(name="user")
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
	
	public Date getResSchedule()
	{
		return this.resSchedule;
	}
	
	public Date getResScheduleEnd()
	{
		return this.resScheduleEnd;
	}
	
	public Users getUser() {
		return user;
	}
	
	public Reservables getReservable() {
		return reservable;
	}
	
	public String getResDescription() {
		return resDescription;
	}
	
	public String getNumPeople() {
		return numPeople;
	}
	
	public String getAdditionalComments() {
		return additionalComments;
	}
	
	public int getRes_ID() {
		return id;
	}
}
