package senior.design.group10.objects.tv;

import javax.persistence.*;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pi")
public class Pi
{
	@Id
	@Column(name = "pi_id")
	@GenericGenerator(name = "generator", strategy= "increment")
	@GeneratedValue(generator="generator")
	private int id;
	
	@Column
	private String ip;
	@Column
	private String host;
	@Column
	private String pasword;
	
	public Pi(String ip, String host, String password)
	{
		this.ip = ip;
		this.host = host;
		this.pasword = password;
	}
}
