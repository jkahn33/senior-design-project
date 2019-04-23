package senior.design.group10.objects.tv;

import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import senior.design.group10.objects.user.Admin;

@Entity
@Table(name = "future")
public class Future 
{
	@Id
	@Column(name="future_id")
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private int id;
	
	@Column
	private String future;
	@Column
	private Timestamp futureEndDate;
	@Column
	private Timestamp futureStartDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Admin admin;
	
	public Future() {};
	
	public Future(String future, Timestamp futureEndDate, Timestamp futureStartDate, Admin admin)
	{
		this.future = future;
		this.futureEndDate = futureEndDate;
		this.futureStartDate = futureStartDate;
		this.admin = admin;
	}
	
	public String getFuture()
	{
		return future;
	}
	
	public Timestamp getFutureEndDate()
	{
		return futureEndDate;
	}
	
	public Timestamp getFutureStartDate()
	{
		return futureStartDate;
	}
	
	public Admin admin()
	{
		return admin;
	}
	/*
	public String getEndHours()
	{
		String endHours  = ""+futureEndDate.getHours();
		return endHours;
	}
	public String getStartHours()
	{
		String startHours  = ""+futureStartDate.getHours();
		return startHours;
	}*/
	
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
