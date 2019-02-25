package senior.design.group10.objects.sent;

public class SentPi
{
	private String host;
	private String ip;
	private String password;
	
	public SentPi() {}
	
	public SentPi( String ip, String host, String password)
	{
		this.ip=ip;
		this.host = host;
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public String getIp() {
		return ip;
	}

	public String getPassword() {
		return password;
	}
	
}
