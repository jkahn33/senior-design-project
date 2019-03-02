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
	private String user;
	@Column
	private String password;
	
	public Pi() {};
	
	public Pi(String ip, String user, String password)
	{
		this.ip = ip;
		this.user = user;
		this.password = password;
	}
	
	public String getIP()
	{
		return ip;
	}
	public String getUser()
	{
		return user;
	}
	public String getPassword()
	{
		return password;
	}
	
}
