package senior.design.group10.service;
import com.jcraft.jsch.*;

import ch.qos.logback.core.joran.conditional.IfAction;
import senior.design.group10.dao.PiDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentPi;
import senior.design.group10.objects.tv.Pi;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PiService 
{

	private final
	PiDAO piDAO;
	List<Pi> piList = new ArrayList<Pi>();
	String user = "pi";
	String host = "192.168.1.3";//ip
	String password = "admin";


	@Autowired
	public PiService(PiDAO piDAO)
	{
		this.piDAO = piDAO;
	}
	
	public void piListFill()
	{
		//If list is full empty then refill
		if (!piList.isEmpty())
			piList.clear();
		
		//get the list of pis from the db then add to the pi list to be used to send the photo to the
		// lis of displays
		Iterable <Pi> piIt = piDAO.findAll();
		//For every pi in Pi iterable add to list
		for (Pi pi : piIt)
			piList.add(pi);
				
		System.out.print(piList.get(0).getIP());
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

	//Get the list of pis from the db and create a list to connect to them
	//public List<Pi> getPiList()
	{
		
	}
	
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

	public void createPiImageFolder(String folderName) {
        String s;
        Process p;
        
        //Remove folder if exists
        try {
            p = Runtime.getRuntime().exec("rm -R " + folderName);
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
        
        //Create new Folder
        try {
            p = Runtime.getRuntime().exec("mkdir " + folderName);
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
    }
	//Assuming that the pi list is full and contains all pis to be update
	//scp the photos onto the list of pis contained
	public void copyImgToPi(String lfile, String rfile)
	{
		/*public static void main(String[] arg){
		    if(arg.length!=2){
		      System.err.println("usage: java ScpTo file1 user@remotehost:file2");
		      System.exit(-1);
		    }      
		 */

		FileInputStream fis=null;
		try{

			//String lfile=arg[0];
			//String rfile=arg[1].substring(arg[1].indexOf(':')+1);

			JSch jsch=new JSch();
			Session session=jsch.getSession(user, host, 22);

			// username and password will be given via UserInfo interface.
			//UserInfo ui=new MyUserInfo();
			java.util.Properties config = new java.util.Properties(); 

			config.put("StrictHostKeyChecking", "no");


			/////////////////////
			//Here
			//Modify set user info ui to not require ui by giving password and whatever else is required
			/////////////////////////
			//session.setUserInfo(ui);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");

			boolean ptimestamp = true;

			// exec 'scp -t rfile' remotely
			rfile=rfile.replace("'", "'\"'\"'");
			rfile="'"+rfile+"'";
			String command="scp " + (ptimestamp ? "-p" :"") +" -t "+rfile;
			Channel channel=session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out=channel.getOutputStream();
			InputStream in=channel.getInputStream();

			channel.connect();

			if(checkAck(in)!=0){
				System.exit(0);
			}

			File _lfile = new File(lfile);

			if(ptimestamp){
				command="T "+(_lfile.lastModified()/1000)+" 0";
				// The access time should be sent here,
				// but it is not accessible with JavaAPI ;-<
				command+=(" "+(_lfile.lastModified()/1000)+" 0\n"); 
				out.write(command.getBytes()); out.flush();
				if(checkAck(in)!=0){
					System.exit(0);
				}
			}

			// send "C0644 filesize filename", where filename should not include '/'
			long filesize=_lfile.length();
			command="C0644 "+filesize+" ";
			if(lfile.lastIndexOf('/')>0){
				command+=lfile.substring(lfile.lastIndexOf('/')+1);
			}
			else{
				command+=lfile;
			}
			command+="\n";
			out.write(command.getBytes()); out.flush();
			if(checkAck(in)!=0){
				System.exit(0);
			}

			// send a content of lfile
			fis=new FileInputStream(lfile);
			byte[] buf=new byte[1024];
			while(true){
				int len=fis.read(buf, 0, buf.length);
				if(len<=0) break;
				out.write(buf, 0, len); //out.flush();
			}
			fis.close();
			fis=null;
			// send '\0'
			buf[0]=0; out.write(buf, 0, 1); out.flush();
			if(checkAck(in)!=0){
				System.exit(0);
			}
			out.close();

			channel.disconnect();
			session.disconnect();

			System.exit(0);
		}
		catch(Exception e){
			System.out.println(e);
			try{if(fis!=null)fis.close();}catch(Exception ee){}
		}
	}

	static int checkAck(InputStream in) throws IOException
	{
		int b=in.read();
		// b may be 0 for success,
		//          1 for error,
		//          2 for fatal error,
		//          -1
		if(b==0) return b;
		if(b==-1) return b;

		if(b==1 || b==2){
			StringBuffer sb=new StringBuffer();
			int c;
			do {
				c=in.read();
				sb.append((char)c);
			}
			while(c!='\n');
			if(b==1){ // error
				System.out.print(sb.toString());
			}
			if(b==2){ // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}

	public static class MyUserInfo implements UserInfo, UIKeyboardInteractive
	{
		public String getPassword(){ return passwd; }
		public boolean promptYesNo(String str){
			Object[] options={ "yes", "no" };
			int foo=JOptionPane.showOptionDialog(null, 
					str,
					"Warning", 
					JOptionPane.DEFAULT_OPTION, 
					JOptionPane.WARNING_MESSAGE,
					null, options, options[0]);
			return foo==0;
		}

		String passwd;
		JTextField passwordField=(JTextField)new JPasswordField(20);

		public String getPassphrase(){ return null; }
		public boolean promptPassphrase(String message){ return true; }
		public boolean promptPassword(String message){
			Object[] ob={passwordField}; 
			int result=
					JOptionPane.showConfirmDialog(null, ob, message,
							JOptionPane.OK_CANCEL_OPTION);
			if(result==JOptionPane.OK_OPTION){
				passwd=passwordField.getText();
				return true;
			}
			else{ return false; }
		}
		public void showMessage(String message){
			JOptionPane.showMessageDialog(null, message);
		}
		final GridBagConstraints gbc = 
				new GridBagConstraints(0,0,1,1,1,1,
						GridBagConstraints.NORTHWEST,
						GridBagConstraints.NONE,
						new Insets(0,0,0,0),0,0);
		private Container panel;
		public String[] promptKeyboardInteractive(String destination,
				String name,
				String instruction,
				String[] prompt,
				boolean[] echo){
			panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			gbc.weightx = 1.0;
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.gridx = 0;
			panel.add(new JLabel(instruction), gbc);
			gbc.gridy++;

			gbc.gridwidth = GridBagConstraints.RELATIVE;

			JTextField[] texts=new JTextField[prompt.length];
			for(int i=0; i<prompt.length; i++){
				gbc.fill = GridBagConstraints.NONE;
				gbc.gridx = 0;
				gbc.weightx = 1;
				panel.add(new JLabel(prompt[i]),gbc);

				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weighty = 1;
				if(echo[i]){
					texts[i]=new JTextField(20);
				}
				else{
					texts[i]=new JPasswordField(20);
				}
				panel.add(texts[i], gbc);
				gbc.gridy++;
			}

			if(JOptionPane.showConfirmDialog(null, panel, 
					destination+": "+name,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE)
					==JOptionPane.OK_OPTION){
				String[] response=new String[prompt.length];
				for(int i=0; i<prompt.length; i++){
					response[i]=texts[i].getText();
				}
				return response;
			}
			else{
				return null;  // cancel
			}
		}
	}


}



