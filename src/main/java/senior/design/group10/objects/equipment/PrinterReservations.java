package senior.design.group10.objects.equipment;
import org.hibernate.annotations.GenericGenerator;

import senior.design.group10.objects.user.Users;

import javax.persistence.*;
import java.sql.Timestamp;

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
	@Column
	private String jobDescription;
	@Column
	private String jobDuration;
	@Column
	private Timestamp jobSchedule;
	@Column
	private Timestamp jobScheduleEnd;
	@Column
	private String additionalComments;
	@Column
	private String printerID;

	public PrinterReservations(){

	}

	public PrinterReservations(Users user, String fiveDigExt, String jobDescription, String jobDuration, Timestamp jobSchedule, Timestamp jobScheduleEnd, String additionalComments, String printerID) {
		this.user = user;
		this.jobDescription = jobDescription;
		this.jobDuration = jobDuration;
		this.jobSchedule = jobSchedule;
		this.jobScheduleEnd = jobScheduleEnd;
		this.additionalComments = additionalComments;
		this.printerID = printerID;
	}
}
