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
	@JoinColumn(name="user_ext")
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

	public String getName(){
		return user.getName();
	}
	
//	public String getBadgeId(){
//		return user.getBadgeId();
//	}

	public String getJobDescription(){
		return jobDescription;
	}

	public String getPrinter(){
		return reservable.getRoom();
	}
	
	public Date getJobSchedule()
	{
		return this.jobSchedule;
	}
	
	public String getJobDuration() {
		return jobDuration;
	}
	
	public Date getJobScheduleEnd()
	{
		return this.jobScheduleEnd;
	}
}
