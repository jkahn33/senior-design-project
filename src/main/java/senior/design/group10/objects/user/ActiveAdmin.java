package senior.design.group10.objects.user;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "active_admin")
public class ActiveAdmin 
{
	@Column
	private String name;
	@Id
	@Column(length=5)
	private String badgeID;

	public ActiveAdmin(){}
	
	public ActiveAdmin(String name, String badgeID)
	{
		this.name = name;
		this.badgeID = badgeID;
	}

	public String getName() {
		return name;
	}

	public String getBadgeID() {
		return badgeID;
	}
}
