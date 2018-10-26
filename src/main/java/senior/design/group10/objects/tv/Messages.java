package senior.design.group10.objects.tv;
import org.hibernate.annotations.GenericGenerator;


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
	@Column
	private String adminID;

	public Messages(){}
	
	public Messages(String message, Timestamp messageDate, String adminID)
	{
		this.message = message;
		this.messageDate = messageDate;
		this.adminID = adminID;
	}
}
