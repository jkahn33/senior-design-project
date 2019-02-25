package senior.design.group10.service;
import com.jcraft.jsch.*;

import senior.design.group10.dao.PiDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentPi;
import senior.design.group10.objects.tv.Pi;

import java.awt.*;
import java.io.InputStream;
import java.util.Optional;

import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PiService 
{
	
	private final
	PiDAO piDAO;
	String user = "pi";
	String host = "192.168.1.3";//ip
	String password = "admin";

	@Autowired
	public PiService(PiDAO piDAO)
	{
		this.piDAO = piDAO;
	}
	public void ExecComToPi(String command)
	{

		try{

			java.util.Properties config = new java.util.Properties(); 
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Session session=jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");

			Channel channel=session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec)channel).setErrStream(System.err);

			InputStream in=channel.getInputStream();
			channel.connect();
			byte[] tmp=new byte[1024];
			while(true){
				while(in.available()>0){
					int i=in.read(tmp, 0, 1024);
					if(i<0)break;
					System.out.print(new String(tmp, 0, i));
				}
				if(channel.isClosed()){
					System.out.println("exit-status: "+channel.getExitStatus());
					break;
				}
				try{Thread.sleep(1000);}catch(Exception ee){}
			}
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ResponseObject addPi( SentPi sentPi)
	{
		Optional<Pi> piOptional = piDAO.checkIP(sentPi.getIp());
		
		if(piOptional.isPresent())
		{
			return new ResponseObject(false, "Pi with " + sentPi.getIp() + " ip already exists");
		}
		
		
		Pi piToAdd = new Pi(sentPi.getIp(),sentPi.getHost(),sentPi.getPassword());
		piDAO.save(piToAdd);
		return new ResponseObject(true, "Pi added");
	}
}

