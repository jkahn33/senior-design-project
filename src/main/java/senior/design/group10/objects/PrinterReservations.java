package senior.design.group10.objects;
import org.hibernate.annotations.GenericGenerator;

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

	@Column
	private String username;
	@Column
	private String fiveDigExt;
	@Column
	private String jobDescription;
	@Column
	private String jobDuration;
	@Column
	private Timestamp jobSchedule;
	@Column
	private String additionalComments;
	@Column
	private String printerID;

	public PrinterReservations(){}

	public PrinterReservations(String username, String fiveDigExt, String jobDescription, String jobDuration, Timestamp jobSchedule, String additionalComments, String printerID) {
		this.username = username;
		this.fiveDigExt = fiveDigExt;
		this.jobDescription = jobDescription;
		this.jobDuration = jobDuration;
		this.jobSchedule = jobSchedule;
		this.additionalComments = additionalComments;
		this.printerID = printerID;
	}
}
