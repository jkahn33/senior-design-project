package senior.design.group10.objects.user;
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
	@Id
	@Column(length=5)
	private String badgeID;
	@Column
	private String depCode;

	public Users(){}
	
	public Users( String name, Timestamp creationDate, String badgeID, String depCode)
	{
		this.name = name;
		this.creationDate = creationDate;
		this.badgeID = badgeID;
		this.depCode = depCode;
	}

	public String getName() {
		return name;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public String getBadgeID() {
		return badgeID;
	}

	public String getDepCode() {
		return depCode;
	}
}
