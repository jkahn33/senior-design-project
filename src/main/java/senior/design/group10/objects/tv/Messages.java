package senior.design.group10.objects.tv;
import org.hibernate.annotations.GenericGenerator;

import senior.design.group10.objects.user.Admin;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Messages 
{
	@Id
	@Column(name="mes_id")
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private int id;

	@Column
	private String message;
	@Column
	private Timestamp messageEndDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Admin admin;

	public Messages(){}
	
	public Messages(String message, Timestamp messageEndDate, Admin admin)
	{
		this.message = message;
		this.messageEndDate = messageEndDate;
		this.admin = admin;
	}
	
	public int getMes_ID() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Timestamp getMessageEndDate() {
		return messageEndDate;
	}
	
	public Admin getAdmin() {
		return admin;
	}
}
