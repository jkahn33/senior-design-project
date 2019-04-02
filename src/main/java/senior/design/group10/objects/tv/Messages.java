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
	private Timestamp messageDate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Admin admin;

	public Messages(){}
	
	public Messages(String message, Timestamp messageDate, Admin admin)
	{
		this.message = message;
		this.messageDate = messageDate;
		this.admin = admin;
	}
	
	public int getMes_ID() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Timestamp getMessageDate() {
		return messageDate;
	}
	
	public Admin getAdmin() {
		return admin;
	}
}
