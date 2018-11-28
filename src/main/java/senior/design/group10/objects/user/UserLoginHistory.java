package senior.design.group10.objects.user;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="user_login_hist")
public class UserLoginHistory 
{
	@Id
	@Column(name="login_id")
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	private int id;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_ext")
	private Users user;
	@Column
	private Timestamp loginDateTime;

	public UserLoginHistory(){}
	
	public UserLoginHistory(Users user, Timestamp loginDateTime)
	{
		this.user = user;
		this.loginDateTime = loginDateTime;
	}
}
