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
	private String badgeId;
	@Column
	private String depCode;

	public Users(){}
	
	public Users( String name, Timestamp creationDate, String badgeId, String depCode)
	{
		this.name = name;
		this.creationDate = creationDate;
		this.badgeId = badgeId;
		this.depCode = depCode;
	}

	public String getName() {
		return name;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public String getBadgeId() {
		return badgeId;
	}

	public String getDepCode() {
		return depCode;
	}
}
