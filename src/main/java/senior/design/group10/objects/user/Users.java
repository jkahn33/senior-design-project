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
	private String fiveDigExt;
	@Column
	private String depCode;

	public Users(){}
	
	public Users( String name, Timestamp creationDate, String fiveDigExt, String depCode)
	{
		this.name = name;
		this.creationDate = creationDate;
		this.fiveDigExt = fiveDigExt;
		this.depCode = depCode;
	}
}
