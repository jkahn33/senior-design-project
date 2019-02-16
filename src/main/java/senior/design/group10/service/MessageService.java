package senior.design.group10.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

import com.openhtmltopdf.java2d.Java2DRenderer;
import com.openhtmltopdf.java2d.api.DefaultPageProcessor;
import com.openhtmltopdf.java2d.api.FSPageOutputStreamSupplier;
import com.openhtmltopdf.java2d.api.Java2DRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.MessageDAO;
import senior.design.group10.dao.AdminDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentMessage;
import senior.design.group10.objects.tv.Messages;
import senior.design.group10.objects.user.Admin;

import javax.imageio.ImageIO;

@Service
public class MessageService {
    private final static Logger log = Logger.getLogger(MessageService.class.getName());
    private final MessageDAO messageDAO;
    private final AdminDAO adminDAO;

    @Autowired
    public MessageService(MessageDAO messageDAO, AdminDAO adminDAO) {
        this.messageDAO = messageDAO;
        this.adminDAO = adminDAO;
    }

    public ResponseObject createNewMessage(SentMessage message){
    	Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());
        
        //find the user attached to the five digit extension
        Optional<Admin> adminOptional = adminDAO.findById(message.getAdminID());
        //checking if the five digit extension is valid
        if(!adminOptional.isPresent())
        	return new ResponseObject(false, "Admin with ID " + message.getAdminID() + " cannot be found.");
        
        Messages newMessage = new Messages(message.getMessage(), currentTime, adminOptional.get());
        log.info("admin ID: " + message.getAdminID() + ", time: " + currentTime);
        messageDAO.save(newMessage);
        
        return new ResponseObject(true, adminOptional.get().getName());
    }
    public boolean renderImage(){
//        StringBuilder contentBuilder = new StringBuilder();
//        try {
//            BufferedReader in = new BufferedReader(new FileReader("test.html"));
//            String str;
//            while ((str = in.readLine()) != null) {
//                contentBuilder.append(str);
//            }
//            in.close();
//        } catch (IOException e) {
//            log.severe(e.toString());
//            return false;
//        }
//        String html = contentBuilder.toString();
//        try (OutputStream os = new FileOutputStream("out.pdf")) {
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//
////            InputStream is = new FileInputStream("test.html");
//
//            builder.withFile(new File("test.html"));
//            builder.toStream(os);
//            builder.run();
//        }
//        catch(Exception e){
//            log.severe(e.toString());
//            return false;
//        }
//        try {
//            DefaultPageProcessor processor = new DefaultPageProcessor(zeroBasedPageNumber ->
//                    new FileOutputStream("out.png"), BufferedImage.TYPE_3BYTE_BGR, "png");
//
//            Graphics2D g2d = processor.createLayoutGraphics();
//
//            Java2DRendererBuilder builder = new Java2DRendererBuilder();
//            builder.withFile(new File("test.html"));
//            builder.useLayoutGraphics(g2d);
//            builder.toPageProcessor(processor);
//            builder.runPaged();
//
//            g2d.dispose();
//        }
//        catch(Exception e){
//            log.severe(e.toString());
//            return false;
//        }
        int width = 250;
        int height = 250;

        // Constructs a BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Create a graphics which can be used to draw into the buffered image
        Graphics2D g2d = bufferedImage.createGraphics();

        // fill all the image with white
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        // create a circle with black
        g2d.setColor(Color.black);
        g2d.fillOval(0, 0, width, height);

        // create a string with yellow
        g2d.setColor(Color.yellow);
        g2d.drawString("Java Code Geeks", 50, 120);

        // Disposes of this graphics context and releases any system resources that it is using.
        g2d.dispose();

        try {
            // Save as PNG
            File file = new File("myimage.png");
            ImageIO.write(bufferedImage, "png", file);

            // Save as JPEG
            file = new File("myimage.jpg");
            ImageIO.write(bufferedImage, "jpg", file);
        }
        catch (Exception e){
            log.severe(e.toString());
            return false;
        }
        return true;
        // align='center' style='font-size:50;color:#ffffff'
    }
}
