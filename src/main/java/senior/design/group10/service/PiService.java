package senior.design.group10.service;
import com.jcraft.jsch.*;

import java.awt.*;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class PiService 
{
	String user = "pi";
	String host = "192.168.1.3";//ip
	String password = "admin";

	public void execComToPi(String command)
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
	
	public void autoUpdateMidnight()
	{
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date;
		try 
		{
			date = dateFormatter .parse("2012-07-06 13:05:45");
			 //Now create the time and schedule it
		    Timer timer = new Timer();

		    //Use this if you want to execute it once
		    timer.schedule(new TimeTask(), date);

		    //Use this if you want to execute it repeatedly
		    int period = 5000;//10secs
		    timer.schedule(new TimeTask(), date, period );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print("does it get here?");

	}
	
	private class TimeTask extends TimerTask
	{
		public void run()
		{
			// insert update 24 hours here
			System.out.println("5 secs past");
		}
	}
}

