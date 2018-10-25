package main.java.userData;
import java.sql.Timestamp;

import javax.persistence.*;



@Entity
@Table(name = "users")
public class Users 
{
	
	@Column
	private String name;
	@Column
	private Timestamp creationDate;
	@Column
	private String fiveDigExt;
	@Column
	private String depCode;
	
	public Users( String name, Timestamp creationDate, String fiveDigExt, String depCode)
	{
		this.name = name;
		this.creationDate = creationDate;
		this.fiveDigExt = fiveDigExt;
		this.depCode = depCode;
	}
}
