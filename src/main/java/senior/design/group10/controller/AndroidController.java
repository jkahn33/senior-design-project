package senior.design.group10.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.ValidateWrapper;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.StringWrapper;
import senior.design.group10.objects.sent.SentBreakoutReservation;
import senior.design.group10.objects.sent.SentCalendar;
import senior.design.group10.objects.sent.SentEquipment;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.sent.SentReservable;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.tv.Calendar;
import senior.design.group10.objects.user.Users;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.BreakoutService;
import senior.design.group10.service.CalendarService;
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
    CalendarService calendarService;

    @Autowired
    public AndroidController(UserService userService,
                             AdminService adminService,
                             LoginService loginService,
                             PrinterService printerService,
                             BreakoutService breakoutService,
                             ReservablesService reservablesService,
                             EquipmentService equipmentService,
                             CalendarService calendarService) {
        this.userService = userService;
        this.adminService = adminService;
        this.loginService = loginService;
        this.printerService = printerService;
        this.breakoutService = breakoutService;
        this.reservablesService = reservablesService;
        this.equipmentService = equipmentService;
        this.calendarService = calendarService;
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
        return loginService.saveNewLogin(login);
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
    public ResponseObject newBreakoutReservation(@RequestBody SentBreakoutReservation breakoutReservation)
	{
		//List for passing in in sent breakout reservation
		List<String> reservableIdList = new ArrayList<>(); //used for testing
		reservableIdList.add("B");
		reservableIdList.add("D");
		
		//Manual breakout reservation entry for testing image rendering
		//breakoutReservation = new SentBreakoutReservation("11111","Breakout",reservableIdList,"celebrate good times", "2019-03-20 05:01:01", "2019-03-20 17:00:00", "2","none");
        log.info(Arrays.toString(breakoutReservation.getReservableIdList().toArray()));
		return breakoutService.addBreakRes(breakoutReservation);
    }

    @PostMapping("/deletePrinterById")
    @ResponseBody
    public ResponseObject deletePrinterResById(@RequestBody StringWrapper id){
        return printerService.deleteById(id.getString());
    }

    @PostMapping("/deleteBreakoutById")
    @ResponseBody
    public ResponseObject deleteBreakoutResById(@RequestBody StringWrapper id){
	    return breakoutService.deleteById(id.getString());
    }


	//Make a function to check that all of the rooms are available for reservations
	//Response object will send true if all rooms are available
	//false if not all rooms are available with unavailable rooms
	//Run this function before proceeding with the add breakout reservation (ask the user if the want to proceed with the reservation
	//even though the room is unavailable
	@PostMapping("/checkSelectedRooms")
	@ResponseBody
	public ResponseObject checkSelectedRooms(@RequestBody SentBreakoutReservation breakoutReservation)
	{
		return breakoutService.checkRoomsAvailable(breakoutReservation);
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
    public ResponseObject checkinEquipment(@RequestBody StringWrapper barcode){
        return equipmentService.checkin(barcode.getString());
    }
    
    @PostMapping("/checkoutEquipment")
    @ResponseBody
    public ResponseObject checkoutEquipment(@RequestBody SentEquipment equipment){
        return equipmentService.checkout(equipment);
    }
    
    /*
     * Saves a new event to the database.
     * Internally, an event is referred to as "calendar" object.
     * Calendar objects contain:
     * -a name
     * -an administrator-specified date
     * -an associated administrator account
     */
    @PostMapping("/newEvent")
    @ResponseBody
    public ResponseObject newEvent(SentCalendar sentCalendar) {
        return calendarService.createNewEvent(sentCalendar);
    }
    
    /*
     * Takes a timestamp.
     * Returns all events on the same day as the given timestamp.
     */
    @PostMapping("/getEventsByDate")
    @ResponseBody
    public List<Calendar> getEventsByDate(Timestamp ts) {
        return calendarService.getEventsByDate(ts);
    }

    @GetMapping("/getPrinterReservations")
    @ResponseBody
    public List<PrinterReservations> getPrinterReservations(){
        return printerService.getPrinterReservations();
    }

    @PostMapping("/getPrinterReservationsId")
    @ResponseBody
    public List<PrinterReservations> getPrinterReservationsId(@RequestBody List<StringWrapper> barcode){
        //log.info(barcode.get(0).getString());
        return printerService.getPrinterReservationId(barcode.get(0).getString());
        //return printerService.getPrinterReservationId("12345");
    }

    @PostMapping("/getPrintById")
    @ResponseBody
    public PrinterReservations getPrinterReservationById(@RequestBody StringWrapper id){
        return printerService.getPrinterReservationById(id.getString());
    }

    @GetMapping("/getBreakoutReservations")
    @ResponseBody
    public List<BreakoutReservations> getBreakoutReservations(){
        return breakoutService.getBreakoutReservations();
    }

    @PostMapping("/getBreakoutReservationsId")
    @ResponseBody
    public List<BreakoutReservations> getBreakoutReservationsId(@RequestBody List<StringWrapper> barcode){
        return breakoutService.getBreakoutReservationId(barcode.get(0).getString());
    }
}
