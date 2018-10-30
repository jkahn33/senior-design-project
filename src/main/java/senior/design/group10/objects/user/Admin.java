package senior.design.group10.objects.user;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="admin")
public class Admin 
{
	@Id
	@Column(length=5)
	private String ext;
	@Column
	private String password;
	@Column
	private String name;
	@Column
	private Timestamp creationDate;

	public Admin(){}
	
	public Admin(String ext, String password, String name, Timestamp creationDate)
	{
		this.ext = ext;
		this.password = password;
		this.name = name;
		this.creationDate = creationDate;
	}
	public String getPassword(){
		return password;
	}

	public String getExt() {
		return ext;
	}

	public String getName() {
		return name;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}
}
