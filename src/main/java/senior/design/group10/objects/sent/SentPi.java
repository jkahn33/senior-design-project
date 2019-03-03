package senior.design.group10.objects.sent;

public class SentPi
{
	private String user;
	private String ip;
	private String password;
	
	public SentPi() {}
	
	public SentPi( String ip, String user, String password)
	{
		this.ip=ip;
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public String getIp() {
		return ip;
	}

	public String getPassword() {
		return password;
	}
	
}
