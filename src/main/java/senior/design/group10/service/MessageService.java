package senior.design.group10.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.MessageDAO;
import senior.design.group10.dao.AdminDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentMessage;
import senior.design.group10.objects.tv.Messages;
import senior.design.group10.objects.user.Admin;

import javax.xml.parsers.ParserConfigurationException;

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
    private static void generatePDFFromHTML(String filename) throws ParserConfigurationException, IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/output/html.pdf"));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
        document.close();
    }
    public boolean renderImage(){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("test.html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            log.severe(e.toString());
            return false;
        }
        String html = contentBuilder.toString();

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream("html.pdf"));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(filename));
        document.close();
        return true;
    }
}
