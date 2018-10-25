<<<<<<< HEAD
package main.java.equipmentData;
import java.sql.Timestamp;


public class BreakoutReservation 
{


	private String username;
	private String fiveDigExt;
	private String jobDescription;
	private String jobDuration;
	private Timestamp jobSchedule;
	private String additionalComments;
	private String roomID;
=======
package equipmentData;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "breakout_res")

public class BreakoutReservation 
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
	private String roomID;
	
	
	public BreakoutReservation(String username, String fiveDigExt, String jobDescription, String jobDuration, Timestamp jobSchedule, String additionalComments, String roomID) {
		this.username = username;
		this.fiveDigExt = fiveDigExt;
		this.jobDescription = jobDescription;
		this.jobDuration = jobDuration;
		this.jobSchedule = jobSchedule;
		this.additionalComments = additionalComments;
		this.roomID = roomID;
	}
>>>>>>> master
}
