package senior.design.group10.service;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.equipment.Reservables;
import senior.design.group10.objects.tv.Calendar;
import senior.design.group10.objects.tv.Messages;
import senior.design.group10.objects.user.Admin;
import senior.design.group10.objects.user.UserLoginHistory;
import senior.design.group10.objects.user.Users;

public class CSVExport {
	@Autowired
	private PasswordEncoder passwordencoder;
	
    private static void writeCols(PrintWriter writer, String[] cols) {
    	writer.write(cols[0]);
    	for(int i = 1; i < cols.length; i++) writer.write("," + cols[i]);
    	writer.write("\n");
    }
    public static void exportUsers(PrintWriter writer, List<Users> users)  {
        try {
            ColumnPositionMappingStrategy<Users> ms = new ColumnPositionMappingStrategy<Users>();
            ms.setType(Users.class);

            String[] cols = new String[]{"name", "creationDate", "badgeId", "depCode"};
            writer.write(cols[0]);
            for(int i = 1; i < cols.length; i++) writer.write("," + cols[i]);
            writer.write("\n");
            ms.setColumnMapping(cols);

            StatefulBeanToCsv<Users> btcsv = new StatefulBeanToCsvBuilder<Users>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(ms)
                    .withSeparator(',')
                    .build();
            btcsv.write(users);
        } catch (CsvException e) {
            System.out.println("CSV error");
        }
    }
    
    public static void exportBreakoutRes(PrintWriter writer, List<BreakoutReservations> res)  {
        try {
            ColumnPositionMappingStrategy<BreakoutReservations> ms = new ColumnPositionMappingStrategy<BreakoutReservations>();
            ms.setType(BreakoutReservations.class);

            String[] cols = new String[]{"res_id", "user", "reservable", "resDescription", "resSchedule",
            							"resScheduleEnd", "numPeople", "additionalComments"};
            writer.write(cols[0]);
            for(int i = 1; i < cols.length; i++) writer.write("," + cols[i]);
            writer.write("\n");
            ms.setColumnMapping(cols);

            StatefulBeanToCsv<BreakoutReservations> btcsv = new StatefulBeanToCsvBuilder<BreakoutReservations>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(ms)
                    .withSeparator(',')
                    .build();
            btcsv.write(res);
        } catch (CsvException e) {
            System.out.println("CSV error");
        }
    }
    
    public static void exportEquipment(PrintWriter writer, List<Equipment> equipment) {
        try {
            ColumnPositionMappingStrategy<Equipment> ms = new ColumnPositionMappingStrategy<Equipment>();
            ms.setType(Equipment.class);

            String[] cols = new String[]{"barcode", "equipmentName", "inStock"};
            writer.write(cols[0]);
            for(int i = 1; i < cols.length; i++) writer.write("," + cols[i]);
            writer.write("\n");
            ms.setColumnMapping(cols);

            StatefulBeanToCsv<Equipment> btcsv = new StatefulBeanToCsvBuilder<Equipment>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(ms)
                    .withSeparator(',')
                    .build();
            btcsv.write(equipment);
        } catch (CsvException e) {
            System.out.println("CSV error");
        }
    }
    
    public static void exportPrinterRes(PrintWriter writer, List<PrinterReservations> equipment) {
        try {
            ColumnPositionMappingStrategy<PrinterReservations> ms = new ColumnPositionMappingStrategy<PrinterReservations>();
            ms.setType(PrinterReservations.class);
        	
            String[] cols = new String[]{"res_id", "user", "reservable", "jobDescription", "jobDuration",
            							"jobSchedule", "jobScheduleEnd", "additionalComments"};
            writer.write(cols[0]);
            for(int i = 1; i < cols.length; i++) writer.write("," + cols[i]);
            writer.write("\n");
            ms.setColumnMapping(cols);

            StatefulBeanToCsv<PrinterReservations> btcsv = new StatefulBeanToCsvBuilder<PrinterReservations>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(ms)
                    .withSeparator(',')
                    .build();
            btcsv.write(equipment);
        } catch (CsvException e) {
            System.out.println("CSV error");
        }
    }
    
    public static void exportLogins(PrintWriter writer, List<UserLoginHistory> logins) {
        try {
            ColumnPositionMappingStrategy<UserLoginHistory> ms = new ColumnPositionMappingStrategy<UserLoginHistory>();
            ms.setType(UserLoginHistory.class);
        	
            String[] cols = new String[]{"login_id", "user", "logindatetime"};
            writer.write(cols[0]);
            for(int i = 1; i < cols.length; i++) writer.write("," + cols[i]);
            writer.write("\n");
            ms.setColumnMapping(cols);

            StatefulBeanToCsv<UserLoginHistory> btcsv = new StatefulBeanToCsvBuilder<UserLoginHistory>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(ms)
                    .withSeparator(',')
                    .build();
            btcsv.write(logins);
        } catch (CsvException e) {
            System.out.println("CSV error");
        }
    }
    
    public static void exportMessages(PrintWriter writer, List<Messages> messages) {
        try {
            ColumnPositionMappingStrategy<Messages> ms = new ColumnPositionMappingStrategy<Messages>();
            ms.setType(Messages.class);
        	
            String[] cols = new String[]{"mes_id", "message", "messageenddate", "admin"};
            writer.write(cols[0]);
            for(int i = 1; i < cols.length; i++) writer.write("," + cols[i]);
            writer.write("\n");
            ms.setColumnMapping(cols);

            StatefulBeanToCsv<Messages> btcsv = new StatefulBeanToCsvBuilder<Messages>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(ms)
                    .withSeparator(',')
                    .build();
            btcsv.write(messages);
        } catch (CsvException e) {
            System.out.println("CSV error");
        }
    }
    
    public static void exportEvents(PrintWriter writer, List<Calendar> events) {
        try {
            ColumnPositionMappingStrategy<Calendar> ms = new ColumnPositionMappingStrategy<Calendar>();
            ms.setType(Calendar.class);
        	
            String[] cols = new String[]{"cal_id", "eventName", "eventDate", "admin"};
            writer.write(cols[0]);
            for(int i = 1; i < cols.length; i++) writer.write("," + cols[i]);
            writer.write("\n");
            ms.setColumnMapping(cols);

            StatefulBeanToCsv<Calendar> btcsv = new StatefulBeanToCsvBuilder<Calendar>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(ms)
                    .withSeparator(',')
                    .build();
            btcsv.write(events);
        } catch (CsvException e) {
            System.out.println("CSV error");
        }
    }
    
    public static void exportAdmins(PrintWriter writer, List<Admin> admins) {
        try {
            ColumnPositionMappingStrategy<Admin> ms = new ColumnPositionMappingStrategy<Admin>();
            ms.setType(Admin.class);
        	
            String[] cols = new String[]{"badgeID", "name", "creationdate"};
            writeCols(writer, cols);
            ms.setColumnMapping(cols);

            StatefulBeanToCsv<Admin> btcsv = new StatefulBeanToCsvBuilder<Admin>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(ms)
                    .withSeparator(',')
                    .build();
            btcsv.write(admins);
        } catch (CsvException e) {
            System.out.println("CSV error");
        }
    }
}
