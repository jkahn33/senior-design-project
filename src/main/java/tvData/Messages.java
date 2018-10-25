package tvData;
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
	
	private String message;
	private Timestamp messageDate;
	private String adminID;
	
	public Messages(String message, Timestamp messageDate, String adminID)
	{
		this.message = message;
		this.messageDate = messageDate;
		this.adminID = adminID;
	}
}
