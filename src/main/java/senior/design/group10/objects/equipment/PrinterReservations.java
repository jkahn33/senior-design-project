/*
 * Class handling the printer reservations table and data manipulation
 * 
 */


package senior.design.group10.objects.equipment;
import org.hibernate.annotations.GenericGenerator;

import senior.design.group10.objects.user.Users;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="printer_res")
public class PrinterReservations
{
	@Id
	@Column(name="res_id")
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private int id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user")
	private Users user;
	@ManyToOne
	private Reservables reservable;
	@Column
	private String jobDescription;
	@Column
	private String jobDuration;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date jobSchedule;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date jobScheduleEnd;
	@Column
	private String additionalComments;

	public PrinterReservations(){

	}

	public PrinterReservations(Users user, Reservables reservable, String jobDescription, String jobDuration, Timestamp jobSchedule, Timestamp jobScheduleEnd, String additionalComments) {
		this.user = user;
		this.reservable = reservable;
		this.jobDescription = jobDescription;
		this.jobDuration = jobDuration;
		this.jobSchedule = jobSchedule;
		this.jobScheduleEnd = jobScheduleEnd;
		this.additionalComments = additionalComments;
	}
	
	public Date getJobSchedule() {
		return this.jobSchedule;
	}
	
	public Date getJobScheduleEnd() {
		return this.jobScheduleEnd;
	}
	
	public Users getUser() {
		return user;
	}
	
	public Reservables getReservable() {
		return reservable;
	}
	
	public String getJobDescription() {
		return jobDescription;
	}
	
	public String getJobDuration() {
		return jobDuration;
	}
	
	public String getAdditionalComments() {
		return additionalComments;
	}
	
	public int getRes_ID() {
		return id;
	}
}
