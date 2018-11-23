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
	//@MapsId("reserable")
	@ManyToOne
	//@JoinColumn(name = "reservable")
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
	
	public Date getJobSchedule()
	{
		return this.jobSchedule;
	}
	
	public Date getJobScheduleEnd()
	{
		return this.jobScheduleEnd;
	}
}
