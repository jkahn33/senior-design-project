package senior.design.group10.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.ValidateWrapper;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.EquipmentWrapper;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.objects.sent.SentBreakoutReservation;
import senior.design.group10.objects.sent.SentEquipment;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.sent.SentReservable;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.user.Users;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.BreakoutService;
import senior.design.group10.service.EquipmentService;
import senior.design.group10.service.UserService;
import senior.design.group10.service.PrinterService;
import senior.design.group10.service.ReservablesService;
import senior.design.group10.objects.sent.SentLoginHistory;
import senior.design.group10.objects.sent.SentMessage;
import senior.design.group10.service.LoginService;
import senior.design.group10.service.MessageService;

@Controller
@RequestMapping("/android")
public class AndroidController {
    private final static Logger log = Logger.getLogger(AndroidController.class.getName());

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
    private final
    MessageService messageService;
    

    @Autowired
    public AndroidController(UserService userService,
                             AdminService adminService,
                             LoginService loginService,
                             PrinterService printerService,
                             BreakoutService breakoutService,
                             ReservablesService reservablesService,
                             EquipmentService equipmentService,
                             MessageService messageService) {
        this.userService = userService;
        this.adminService = adminService;
        this.loginService = loginService;
        this.printerService = printerService;
        this.breakoutService = breakoutService;
        this.reservablesService = reservablesService;
        this.equipmentService = equipmentService;
        this.messageService = messageService;
    }
    
    //API endpoint for creating a new user.
    @PostMapping("/newUser")
    @ResponseBody
    public ResponseObject newUser(@RequestBody SentUser sentUser){
        return userService.saveNewUser(sentUser);
    }

    @PostMapping("/validateAdmin")
    @ResponseBody
    public ValidateWrapper validateAdmin(@RequestBody AdminInQuestion adminInQuestion){
        return new ValidateWrapper(adminService.isAdminValid(adminInQuestion));
    }

    @PostMapping("/newEquipment")
    @ResponseBody
    public ResponseObject newEquipment(@RequestBody Equipment equipment) {
        return equipmentService.addNewEquipment(equipment);
    }

    @PostMapping("/storeLogin")
    @ResponseBody
    public ResponseObject storeLogin(@RequestBody SentLoginHistory login) {
    	ResponseObject response = loginService.saveNewLogin(login);
    	System.out.println(response.isSuccess() + " " + response.getMessage());
        return response;
    }
    
    //Just for testing. UserService.getAllUsers() returns a list of all users
    @GetMapping("/printAllUsers")
    @ResponseBody
    public List<Users> printAllUsers() {
        return userService.getAllUsers();
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
	public ResponseObject newPrinterReservation(@RequestBody SentPrinterReservation printerReservation)
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
    public ResponseObject newBreakoutReservation(@RequestBody SentBreakoutReservation breakoutReservation) {
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
    public ResponseObject newReservable(@RequestBody SentReservable sentReservable)
    {
        return reservablesService.saveNewReservable(sentReservable);
    }
    /*
     * Removing unwanted reservable by taking in type and id
     */
    
    @PostMapping("/removeReservable")
    @ResponseBody
    public ResponseObject removeReservable(@RequestBody SentReservable sentReservable)
    {
    		return reservablesService.removeReservable(sentReservable);
    }
    @PostMapping("/checkinEquipment")
    @ResponseBody
    public ResponseObject checkinEquipment(@RequestBody EquipmentWrapper barcode){
        return equipmentService.checkin(barcode.getBarcode());
    }
    
    /*
     * Saves a new message to the database.
     * Messages contain:
     * -a string (the message)
     * -a timestamp of creation
     * -an associated administrator account
     */
    @GetMapping("/newMessage")
    @ResponseBody
    public ResponseObject newMessage(SentMessage sentMessage) {
    	SentMessage testMsg = new SentMessage("this is a test", "77777");
        return messageService.createNewMessage(testMsg);
    }
    
    @GetMapping("/createTestAdmin")
    @ResponseBody
    public ResponseObject createTestAdmin() {
    	NewAdmin testAdmin = new NewAdmin("test", "11111", "password");
        return adminService.createNewAdmin(testAdmin);
    }
}
