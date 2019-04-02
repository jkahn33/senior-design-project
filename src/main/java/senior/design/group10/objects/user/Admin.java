package senior.design.group10.objects.user;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="admin")
public class Admin 
{
	@Id
	@Column(length=5)
	private String badgeID;
	@Column
	private String password;
	@Column
	private String name;
	@Column
	private Timestamp creationDate;

	public Admin(){}
	
	public Admin(String badgeID, String password, String name, Timestamp creationDate)
	{
		this.badgeID = badgeID;
		this.password = password;
		this.name = name;
		this.creationDate = creationDate;
	}

	public String getBadgeID() {
		return badgeID;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getName() {
		return name;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setBadgeID(String badgeID) {
		this.badgeID = badgeID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return badgeID + ":" + name;
	}
}
