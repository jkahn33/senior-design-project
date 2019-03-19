package senior.design.group10.service;
import com.jcraft.jsch.*;

import ch.qos.logback.core.joran.conditional.IfAction;
import senior.design.group10.dao.PiDAO;
import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentPi;
import senior.design.group10.objects.tv.Messages;
import senior.design.group10.objects.tv.Pi;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PiService 
{

	private final
	PiDAO piDAO;
	List<Pi> piList = new ArrayList<Pi>();
	private final static Logger log = Logger.getLogger(PiService.class.getName());

	@Autowired
	public PiService(PiDAO piDAO)
	{
		this.piDAO = piDAO;
	}


	/*///////////////////////////////////////////////
	 * Image Rendering
	 */////////////////////////////////////////////

	public boolean renderBreakoutImage(List<BreakoutReservations> breakoutList)
	{

		//Create a switch to choose header for image to display
		String headerText= "USWRIC Today's Breakout Events";

		//make size fit 1080p tv
		int width = 1920;
		int height = 1080;

		// Constructs a BufferedImage of one of the predefined image types.
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();
		//used to setup Java rendering algorithms
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		//render the gradient blue/yellow background
		GradientPaint paint = new GradientPaint(0, 0, Color.BLUE, 0, height, Color.ORANGE);
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, width, height);

		//render the header text
		Font font = new Font("Arial", Font.BOLD, 100);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int x = ((width - fm.stringWidth(headerText)) / 2);

		g2d.setColor(Color.BLACK);
		g2d.drawString(headerText, x, 150);

		//rendering of administrative messages
		x = 64;
		int y = 300;
		ArrayList<String> testArr = new ArrayList<>();

		//for every string in messages add to testArr
		for( int loop = 0; loop< breakoutList.size();loop++)
		{
			String message = breakoutList.get(loop).getresDescription() + " Room: " + breakoutList.get(loop).getRoom()
					+ ", From " + breakoutList.get(loop).getStartHours()+ " to "+ breakoutList.get(loop).getEndHours();
			testArr.add(message);
		}
		//testArr.add("Printer number 3 is broken. Please refrain from using printer number 3. Test1 test2 test3 test4. Test5 test6 test7 test8.");
		//testArr.add("The USWRIC will be closed to all visitors on Friday, February 22nd.");

		font = new Font("Arial", Font.BOLD, 48);
		g2d.setFont(font);

		g2d.setColor(Color.BLACK);

		for(String s : testArr) {
			String[] splits = s.split(" ");
			for(int i = 0; i < splits.length; i++){
				String stringToPrint = getStringToPrint(splits, i);
				String[] printString = stringToPrint.split("@#&");
				g2d.drawString(printString[0], x, y);
				y += 50;

				i = Integer.parseInt(printString[1]);
			}

			g2d.setStroke(new BasicStroke(10));
			g2d.draw(new Line2D.Float(210, y, 1710, y));
			y+=100;
		}

		// Disposes of this graphics context and releases any system resources that it is using.
		g2d.dispose();

		try {
			// Save as PNG
			File file = new File("PiImages/breakout_events.png");
			OutputStream out = new FileOutputStream(file);
			ImageIO.write(bufferedImage, "png", file);
		}
		catch (Exception e){
			log.severe(e.toString());
			return false;
		}
		return true;
	}
	public boolean renderMessagesImage(List<Messages> messageList){
		String headerText = "USWRIC Important Information";
		//make size fit 1080p tv
		int width = 1920;
		int height = 1080;

		// Constructs a BufferedImage of one of the predefined image types.
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Create a graphics which can be used to draw into the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();
		//used to setup Java rendering algorithms
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		//render the gradient blue/yellow background
		GradientPaint paint = new GradientPaint(0, 0, Color.BLUE, 0, height, Color.ORANGE);
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, width, height);

		//render the header text
		Font font = new Font("Arial", Font.BOLD, 100);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		int x = ((width - fm.stringWidth(headerText)) / 2);

		g2d.setColor(Color.BLACK);
		g2d.drawString(headerText, x, 150);

		//rendering of administrative messages
		x = 64;
		int y = 300;
		ArrayList<String> testArr = new ArrayList<>();
		//for every string in messages add to testArr
		for( int loop = 0; loop< messageList.size();loop++)
		{
			testArr.add(messageList.get(loop).getMessage());
		}
		//testArr.add("Printer number 3 is broken. Please refrain from using printer number 3. Test1 test2 test3 test4. Test5 test6 test7 test8.");
		//testArr.add("The USWRIC will be closed to all visitors on Friday, February 22nd.");

		font = new Font("Arial", Font.BOLD, 48);
		g2d.setFont(font);

		g2d.setColor(Color.BLACK);

		for(String s : testArr) {
			String[] splits = s.split(" ");
			for(int i = 0; i < splits.length; i++){
				String stringToPrint = getStringToPrint(splits, i);
				String[] printString = stringToPrint.split("@#&");
				g2d.drawString(printString[0], x, y);
				y += 50;

				i = Integer.parseInt(printString[1]);
			}

			g2d.setStroke(new BasicStroke(10));
			g2d.draw(new Line2D.Float(210, y, 1710, y));
			y+=100;
		}

		// Disposes of this graphics context and releases any system resources that it is using.
		g2d.dispose();

		try {
			// Save as PNG
			File file = new File("PiImages/admin_messages.png");
			OutputStream out = new FileOutputStream(file);
			ImageIO.write(bufferedImage, "png", file);
		}
		catch (Exception e){
			log.severe(e.toString());
			return false;
		}
		return true;
	}
	
	private String getStringToPrint(String[] splits, int index){
		StringBuilder newString = new StringBuilder();
		while(index < splits.length && newString.length() + splits[index].length() < 75){
			newString.append(" ");
			newString.append(splits[index]);
			index++;
		}
		index--;
		newString.append("@#&");
		newString.append(index);
		return newString.toString();
	}

	/////////////////////////////////////////
	////Connecting to pi
	////////////////////////////////////////
	public void piListFill()
	{
		System.out.println("got here");
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

	//Adds pi to db by checking if ip sent matches any existing
	public ResponseObject addPi( SentPi sentPi)
	{
		Optional<Pi> piOptional = piDAO.checkIP(sentPi.getIp());

		if(piOptional.isPresent())
		{
			return new ResponseObject(false, "Pi with " + sentPi.getIp() + " ip already exists");
		}


		Pi piToAdd = new Pi(sentPi.getIp(),sentPi.getUser(),sentPi.getPassword());
		piDAO.save(piToAdd);
		return new ResponseObject(true, "Pi added");
	}


	//Executes linux command to list of pis
	public void execComToPi(String command)
	{


		String user,host,password;

		for (int x = 0; x < piList.size(); x++)
		{
			user = piList.get(x).getUser();
			host = piList.get(x).getIP();
			password = piList.get(x).getPassword();
			FileInputStream fis=null;
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
	}

	///Creates an image folder to be distributed amongst pis. called at fillPi List
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
		
		String user,host,password;

		//Do the file transfer contained in piList
		for (int x = 0; x < piList.size(); x++)
		{
			user = piList.get(x).getUser();
			host = piList.get(x).getIP();
			password = piList.get(x).getPassword();
			FileInputStream fis=null;
			try
			{
				System.out.println("got to the exec1");

				JSch jsch=new JSch();
				Session session=jsch.getSession(user, host, 22);

				java.util.Properties config = new java.util.Properties(); 

				config.put("StrictHostKeyChecking", "no");

				session.setPassword(password);
				session.setConfig(config);
				session.connect();
				System.out.println("Connected to " +piList.get(x).getIP());

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
					//System.exit(0);
					System.out.println("There was an Ack problem");
					return ;}

				File _lfile = new File(lfile);

				/*
			if(ptimestamp){
				command="T "+(_lfile.lastModified()/1000)+" 0";
				// The access time should be sent here,
				// but it is not accessible with JavaAPI ;-<
				command+=(" "+(_lfile.lastModified()/1000)+" 0\n"); 
				out.write(command.getBytes()); out.flush();
				if(checkAck(in)!=0){
					System.out.print("here");

					System.exit(0);
				}
			}
				 */

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
					//System.exit(0);
					System.out.println("There was an Ack problem");
					return;
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
					//System.exit(0);
					System.out.println("There was an Ack problem");
					return;
				}
				out.close();

				channel.disconnect();
				session.disconnect();

				System.out.println("Sucessful file transfer");
			}
			catch(Exception e){
				System.out.println(e);
				try{if(fis!=null)fis.close();}catch(Exception ee){}
			}
		}
	}

	//Assuming that the pi list is full and contains all pis to be update
	//scp the folder specified onto the list of pis contained
	public void copyFolderToPi(String lFolder, String rfile)
	{
		File folder = new File(lFolder);
		File[] listOfFiles = folder.listFiles();

		//For every file contained in 
		for (int i = 0; i < listOfFiles.length; i++)
		{
			if (listOfFiles[i].isFile()) 
			{
				System.out.println("File " + listOfFiles[i].getName());

				String fileLocation = lFolder+ "/" +listOfFiles[i].getName();
				copyImgToPi(fileLocation, rfile);
			} else if (listOfFiles[i].isDirectory()) 
			{
				System.out.println("Directory " + listOfFiles[i].getName());
			}
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


}



