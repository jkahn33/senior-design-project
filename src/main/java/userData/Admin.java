package main.java.userData;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;

@Entity
@Table(name="admin")
public class Admin 
{
	@Id
	
	@Column
	private String adminID;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String name;
	
	public Admin( String adminID, String username, String password, String name)
	{
		this.adminID = adminID;
		this.username = username;
		this.password = password;
		this.name = name;
	}
}
