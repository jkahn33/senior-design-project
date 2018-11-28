package senior.design.group10.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.EquipmentWrapper;
import senior.design.group10.objects.sent.SentBreakoutReservation;
import senior.design.group10.objects.sent.SentEquipment;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.sent.SentReservable;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.BreakoutService;
import senior.design.group10.service.EquipmentService;
import senior.design.group10.service.UserService;
import senior.design.group10.service.PrinterService;
import senior.design.group10.service.ReservablesService;
import senior.design.group10.objects.sent.SentLoginHistory;
import senior.design.group10.service.LoginService;

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
    BreakoutService breakoutService;
    private final
    ReservablesService reservablesService;
    private final
    EquipmentService equipmentService;
    

    @Autowired
    public AndroidController(UserService userService,
                             AdminService adminService,
                             LoginService loginService,
                             PrinterService printerService,
                             BreakoutService breakoutService,
                             ReservablesService reservablesService,
                             EquipmentService equipmentService) {
        this.userService = userService;
        this.adminService = adminService;
        this.loginService = loginService;
        this.printerService = printerService;
        this.breakoutService = breakoutService;
        this.reservablesService = reservablesService;
        this.equipmentService = equipmentService;
    }
    
    //API endpoint for creating a new user.
    @PostMapping("/newUser")
    @ResponseBody
    public ResponseObject newUser(@RequestBody SentUser sentUser){
        return userService.saveNewUser(sentUser);
    }

    @PostMapping("/validateAdmin")
    @ResponseBody
    public boolean validateAdmin(@RequestBody AdminInQuestion adminInQuestion){
        return adminService.isAdminValid(adminInQuestion);
    }

    @PostMapping("/newEquipment")
    @ResponseBody
    public ResponseObject newEquipment(@RequestBody Equipment equipment) {
        return equipmentService.addNewEquipment(equipment);
    }

    @PostMapping("/storeLogin")
    @ResponseBody
    public ResponseObject storeLogin(@RequestBody SentLoginHistory login) {
    	return loginService.saveNewLogin(login);
    }
    //Just for testing. UserService.getAllUsers() returns a list of all users
    @GetMapping("/printAllUsers")
    @ResponseBody
    public void printAllUsers() {
    	System.out.println(userService.getAllUsers());
    }
    
    
    /*
     * Printer reservation takes in a sent printer reservation which is composed of the following variables
     * String for user extension, String for type of reservable, string for reservable id, string for job description
     * TimeStamp for the start date
     * String for job duration in the following format: "HH:MM" since the time is parsed base on the colon
     * String for the additional comments
     */
    @PostMapping("/newPrinterReservation")
	@ResponseBody
	public ResponseObject newPrinterReservation(SentPrinterReservation printerReservation)
	{	
		return printerService.addPrintRes(printerReservation);
	}
    
    /*
     * Breakout reservation takes in a sent breakout reservation which is composed of the following variables
     * String for user extension, String for type of reservable, string for reservable id, string for job description
     * TimeStamp for the start date
     * String for reservation duration in the following format: "HH:MM" since the time is parsed base on the colon
     * String for the number of people
     * String for the additional comments
     */
    @PostMapping("/newBreakoutReservation")
	@ResponseBody
    public ResponseObject newBreakoutReservation(SentBreakoutReservation breakoutReservation) {
        Timestamp timestamp = java.sql.Timestamp.valueOf("2007-09-24 10:19:10");
        String numPeep = "23";
        breakoutReservation = new SentBreakoutReservation("1234", "Breakout", "A", "print boat today", timestamp, "23:00", numPeep, "THis is the additional comment");
        return breakoutService.addBreakRes(breakoutReservation);
    }
    @PostMapping("/checkoutEquipment")
    @ResponseBody
    public ResponseObject checkoutEquipment(@RequestBody SentEquipment equipment){
        return equipmentService.checkout(equipment);
    }
    
    /*
     * Making a new reservable to be used for reservation
     * such as printers and breakout rooms
     * Composed of a sent reservable which contain two strings
     * String for reservable type, String for reservable Id
     */
    @PostMapping("/newReservable")
    @ResponseBody
    public ResponseObject newReservable(SentReservable sentReservable)
    {
    		return reservablesService.saveNewReservable(sentReservable);
    }
    /*
     * Removing unwanted reservable by taking in type and id
     */
    
    @PostMapping("/removeReservable")
    @ResponseBody
    public ResponseObject removeReservable(SentReservable sentReservable)
    {
    		
    		sentReservable = new SentReservable("Printer","B");
    		return reservablesService.removeReservable(sentReservable);
    }
    @PostMapping("/checkinEquipment")
    @ResponseBody
    public ResponseObject checkinEquipment(EquipmentWrapper barcode){
        return equipmentService.checkin(barcode.getBarcode());
    }
}
