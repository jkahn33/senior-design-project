package senior.design.group10.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.SentEquipment;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.sent.SentReservable;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.EquipmentService;
import senior.design.group10.service.UserService;
import senior.design.group10.service.PrinterService;
import senior.design.group10.service.ReservablesService;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.sent.SentLoginHistory;
import senior.design.group10.service.LoginService;
import senior.design.group10.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/android")
public class AndroidController {
    private final
    UserService userService;
    private final
    AdminService adminService;
    private final
    LoginService loginService;
    private final
    PrinterService printerService;
    private final
    ReservablesService reservablesService;

    @Autowired
    public AndroidController(UserService userService, AdminService adminService, LoginService loginService, PrinterService printerService, ReservablesService reservablesService) {
        this.userService = userService;
        this.adminService = adminService;
        this.loginService = loginService;
        this.printerService = printerService;
        this.reservablesService = reservablesService; 
    }
    
    //API endpoint for creating a new user.
    @GetMapping("/newUser")
    @ResponseBody
    public ResponseObject newUser(SentUser sentUser){
    	sentUser = new SentUser("name", "11111", "0123");
        return userService.saveNewUser(sentUser);
    }

    @PostMapping("/validateAdmin")
    @ResponseBody
    public boolean validateAdmin(AdminInQuestion adminInQuestion){
        return adminService.isAdminValid(adminInQuestion);
    }
    @GetMapping("/storeLogin")
    @ResponseBody
    public ResponseObject storeLogin(SentLoginHistory login) {
    	login = new SentLoginHistory("11111");
    	return loginService.saveNewLogin(login);
    }
    //Just for testing. UserService.getAllUsers() returns a list of all users
    @GetMapping("/printAllUsers")
    @ResponseBody
    public void printAllUsers() {
    	System.out.println(userService.getAllUsers());
    }
    
    @GetMapping("/newPrinterReservation")
	@ResponseBody
	public ResponseObject newPrinterReservation(SentPrinterReservation printerReservation)
	{
		Timestamp timestamp = java.sql.Timestamp.valueOf("2007-09-24 10:19:10");
	
		printerReservation = new SentPrinterReservation("1234","Printer","A","print boat today",timestamp,"23:00","THis is the additional comment");

		//printerReservation.ch
		return printerService.addPrintRes(printerReservation);
	}
    
    
    //Working with reservables
    
    @GetMapping("/newReservable")
    @ResponseBody
    public ResponseObject newReservable(SentReservable sentReservable)
    {
    		
    		sentReservable = new SentReservable("Printer","B");
    		return reservablesService.saveNewReservable(sentReservable);
    }
    
    @GetMapping("/removeReservable")
    @ResponseBody
    public ResponseObject removeReservable(SentReservable sentReservable)
    {
    		
    		sentReservable = new SentReservable("Printer","B");
    		return reservablesService.removeReservable(sentReservable);
    }
}
