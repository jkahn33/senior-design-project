package senior.design.group10.objects.tv;

import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import senior.design.group10.objects.user.Admin;

@Entity
@Table(name = "futureMessage")
public class Future 
{
	@Id
	@Column(name="future_id")
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private int id;
	
	@Column
	private String futureMessage;
	@Column
	private Timestamp futureEndDate;
	@Column
	private Timestamp futureStartDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Admin admin;
	
	public Future() {}
	
	public Future(String futureMessage, Timestamp futureEndDate, Timestamp futureStartDate, Admin admin)
	{
		this.futureMessage = futureMessage;
		this.futureEndDate = futureEndDate;
		this.futureStartDate = futureStartDate;
		this.admin = admin;
	}

	public int getId(){
		return id;
	}
	
	public String getFutureMessage()
	{
		return futureMessage;
	}
	
	public Admin admin()
	{
		return admin;
	}
	
	public String getStartDate()
	{
		long ts = futureStartDate.getTime();
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTimeInMillis(ts);
		String startDate =""+ (cal.get(java.util.Calendar.MONTH)+1)+ "/" + cal.get(java.util.Calendar.DATE); 

		return startDate;
	}
	
	public String getEndDate()
	{
		long ts = futureEndDate.getTime();
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTimeInMillis(ts);
		String startDate =""+ (cal.get(java.util.Calendar.MONTH)+1)+ "/" + cal.get(java.util.Calendar.DATE); 

		return startDate;
	}
	
}
