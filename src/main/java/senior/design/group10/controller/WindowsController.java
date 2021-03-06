package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.ReturnAdmin;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.EditAdmin;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.objects.sent.SentMessage;
import senior.design.group10.objects.user.Admin;
import senior.design.group10.objects.user.UserLoginHistory;
import senior.design.group10.objects.tv.Calendar;
import senior.design.group10.objects.tv.Future;
import senior.design.group10.objects.tv.Messages;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.MessageService;
import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.equipment.PrinterUsageUsers;
import senior.design.group10.objects.response.*;
import senior.design.group10.objects.sent.*;
import senior.design.group10.objects.user.Users;
import senior.design.group10.service.*;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

@Controller
@RequestMapping("/windows")
public class WindowsController {
	private final static Logger log = Logger.getLogger(WindowsController.class.getName());

    private final AdminService adminService;
    private final UserService userService;
    private final EquipmentService equipmentService;
    private final MessageService messageService;
    private final PrinterService printerService;
    private final PiService piService;
    private final BreakoutService breakoutService;
    private final FutureService futureService;
    private final LoginService loginService;
    private final CalendarService calendarService;

    @Autowired
    public WindowsController(AdminService adminService,
                             UserService userService,
                             EquipmentService equipmentService,
                             MessageService messageService,
                             PrinterService printerService,
							  PiService piService,
							  BreakoutService breakoutService,
                             FutureService futureService,
                             LoginService loginService,
                             CalendarService calendarService) {
        this.adminService = adminService;
        this.userService = userService;
        this.equipmentService = equipmentService;
        this.messageService = messageService;
        this.printerService = printerService;
        this.piService = piService;
        this.breakoutService = breakoutService;
        this.futureService = futureService;
        this.loginService = loginService;
        this.calendarService = calendarService;
    }

    @PostMapping("/newAdmin")
    @ResponseBody
    public ResponseObject newAdmin(@RequestBody NewAdmin sentAdmin){
	return adminService.createNewAdmin(sentAdmin);
    }

    @PostMapping("/validateAdmin")
    @ResponseBody
    public boolean validateAdmin(@RequestBody AdminInQuestion adminInQuestion, HttpServletResponse response){
        if(adminService.isAdminValid(adminInQuestion)) {
            return adminService.isAdminValid(adminInQuestion);
        }
        else{
            response.setStatus(401);
            return false;
        }
    }

    @PostMapping("/getAdmin")
    @ResponseBody
    public ReturnAdmin getAdmin(@RequestBody EditAdmin editAdmin){
        return adminService.getAdminById(editAdmin.getOldExt());
    }

    @PostMapping("/editAdmin")
    @ResponseBody
    public ResponseObject editAdmin(@RequestBody EditAdmin editAdmin){
        return adminService.editAdmin(editAdmin);
    }

    @GetMapping("/equipmentUsage")
    @ResponseBody
    public List<EquipmentUsageResponse> getEquipmentStatistics(){
        return equipmentService.getUsageStatistics();
    }

    @GetMapping("/userStatistics")
    @ResponseBody
    public List<UsersStatisticResponse> getUserStatistics(/*@RequestBody StatisticsRequest request*/){
        StatisticsRequest request = new StatisticsRequest("2018-12-04 00:00:00", "2018-12-18 11:59:59");
        return userService.getUsersBetweenDates(request);
    }

    @PostMapping("/userSearchId")
    @ResponseBody
    public void userSearchById(@RequestBody StringWrapper stringWrapper){

        return;
    }

    @GetMapping("/getCurrentPrinterReservations")
    @ResponseBody
    public void getCurrentPrinters(){

    }

    @PostMapping("/newEquipment")
    @ResponseBody
    public ResponseObject newEquipment(@RequestBody Equipment equipment) {
        return equipmentService.addNewEquipment(equipment);
    }

    @GetMapping("/getCheckedOutEquipment")
    @ResponseBody
    public List<CheckedOutEquipment> getCheckedOutEquipment(){
        return equipmentService.getCheckedOutEquipment();
    }

    @PostMapping("/getUser")
    @ResponseBody
    public Users getUserById(@RequestBody StringWrapper stringWrapper){
        return userService.getUserById(stringWrapper.getString());
    }

	@PostMapping("/removeUser")
	@ResponseBody
	public ResponseObject removeUser(@RequestBody StringWrapper stringWrapper){
		return userService.removeUser(stringWrapper.getString());
	}

	@PostMapping("/getAdminName")
	@ResponseBody
	public ResponseObject getAdminName(@RequestBody StringWrapper stringWrapper){
		return adminService.getAdminName(stringWrapper.getString());
	}

	@PostMapping("/removeAdmin")
	@ResponseBody
	public ResponseObject removeAdmin(@RequestBody StringWrapper stringWrapper){
		return adminService.removeAdmin(stringWrapper.getString());
	}

	@PostMapping("/getSpecificUser")
    @ResponseBody
    public SpecificUserResponse getSpecificUser(@RequestBody StringWrapper stringWrapper){
        //StringWrapper stringWrapper = new StringWrapper("12345");
        return userService.statsByUser(stringWrapper.getString());
    }

	////////////////////////////////////////////////
	//Pi commands
	//
	////////////////////////////////////////////////

	//Checks for duplicate ip then stores to ip table
	//if duplicate returns false object response
	//else stores in db and will be used to connect to different pis
	@PostMapping("/addPi")
	@ResponseBody
	public ResponseObject addPi(@RequestBody SentPi sentPi)
	{
	    //SentPi sentPi = new SentPi("192.168.1.2", "pi", "admin");
		return piService.addPi(sentPi);        
	}

	
	@GetMapping("/fillPiList")
	public void fillPiList()
	{
		piService.piListFill();
	}
	//Sends the command to the pi to execute through ssh command

	@GetMapping("/execComToPi")
	@ResponseBody
	public ResponseObject sshPi(String command)
	{
		//PiService piservice = new PiService();
		piService.execComToPi(command);
		return new ResponseObject(true, null);
	}
	
	@GetMapping("/sendFileToPi")
	@ResponseBody
	public ResponseObject sendFileToPi()
	{
		piService.copyImgToPi("out.pdf", "Desktop");
		return new ResponseObject(true,null);
	}
	
	@GetMapping("sendFolderToPi")
	@ResponseBody
	public ResponseObject sendFolderToPi()
	{
		
		piService.copyFolderToPi("TestDir", "Desktop/New");
		return new ResponseObject(true,null);
	}

    /////////////////////////////////
	//Messages
	//////////////////////////////
	
    /*
     * Saves a new message to the database.
     * Messages contain:
     * -a string (the message)
     * -a timestamp of creation (automatically generated by the backend)
     * -an associated administrator account
     */
    @PostMapping("/newMessage")
    @ResponseBody
    public ResponseObject newMessage(@RequestBody SentMessage sentMessage) 
    {
        //Sending a message
        //Used for making new messages
        //sentMessage = new SentMessage("entering past message", "12345", "2010-01-01 01:01:01");
        return messageService.createNewMessage(sentMessage);
    }    
    
    @PostMapping("/newFuture")
    @ResponseBody
    public ResponseObject newFuture(@RequestBody SentFuture sentFuture)//Add @request body
    {
    		//for testing
    		//SentFuture test = new SentFuture("This is for test", "12345", "2019-01-01 01:01:01", "2019-10-02 01:01:01");
    		return futureService.createNewFuture(sentFuture);
    }
    
    /*
     * Deletes all messages with date less than current date then returns everything else
     * Messages contain:
     * -a string (the message)
     * -a timestamp of creation (automatically generated by the backend)
     * -an associated administrator account
     */
    @GetMapping("/updatePiImages")
    @ResponseBody
    public ResponseObject updatePiImages()
    {
    		piService.updatePiImages(messageService, breakoutService, futureService);
        return  new ResponseObject(true,null);
    }
    
    @GetMapping("/runSlideShow")
    @ResponseBody
    public ResponseObject startSlideshow()
    {
    		piService.piListFill();
    		updatePiImages();
    		piService.startSlideShow();
    		autoUpdateMidnight();
    		return new ResponseObject(true,null);
    }
    @GetMapping("/printerUsage")
    @ResponseBody
    public List<PrinterUsageResponse> getPrinterUsage(){
        return printerService.getPrinterUsage();
    }
    
    @GetMapping("/getMessages")
    @ResponseBody
    public List<Messages> getMesssages() {
        return messageService.getCurrentMessages();
    }

    @PostMapping("/deleteMessagesById")
    @ResponseBody
    public ResponseObject deleteMessageById(@RequestBody IdWrapper ids)
    {
        messageService.deleteMessagesById(ids.getIds());
        updatePiImages();
        return new ResponseObject(true,null);
    }
    
    @GetMapping("/getFutures")
    @ResponseBody
    public List<Future> getFutures()
    {
        return futureService.getFutureMessages();
    }
    

    @PostMapping("/deleteFuturesById")
    @ResponseBody
    public ResponseObject deleteFutureById(@RequestBody IdWrapper ids)
    {
        futureService.deleteFuturesById(ids.getIds());
        updatePiImages();
		return new ResponseObject(true,null);
    }

    @GetMapping("/printerUserUsage")
    @ResponseBody
    public List<PrinterUsageUsers> getPrinterUserUsage(){
        return printerService.getUserUsage();
    }
    
    /*CSV Export Endpoints*/
    @RequestMapping(value = "/csv/users", produces = "text/csv")
	public void exportUsers(HttpServletResponse response) throws IOException {
		List<Users> users = userService.getAllUsers();
		CSVExport.exportUsers(response.getWriter(), users);
	}

	@RequestMapping(value = "/csv/breakoutres", produces = "text/csv")
	public void exportBreakoutRes(HttpServletResponse response) throws IOException {
		List<BreakoutReservations> res = breakoutService.getBreakoutReservations();
		CSVExport.exportBreakoutRes(response.getWriter(), res);
	}

	@RequestMapping(value = "/csv/equipment", produces = "text/csv")
	public void exportEquipment(HttpServletResponse response) throws IOException {
		List<Equipment> equipment = equipmentService.getAllEquipment();
		CSVExport.exportEquipment(response.getWriter(), equipment);
	}

	@RequestMapping(value = "/csv/printerres", produces = "text/csv")
	public void exportPrinterRes(HttpServletResponse response) throws IOException {
        List<PrinterReservations> res = printerService.getPrinterReservations();
        CSVExport.exportPrinterRes(response.getWriter(), res);
    }
    @GetMapping("/setAutoUpdate")
    @ResponseBody
    public ResponseObject setAutoUpdate()
    {
    		autoUpdateMidnight();
    		return new ResponseObject(true, null);
    }
    
    public void autoUpdateMidnight()
	{
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try 
		{
			date = dateFormatter.parse("2019-01-01 1:00:00");
			//Now create the time and schedule it
			Timer timer = new Timer();

			//Use this if you want to execute it once
			timer.schedule(new TimeTask(), date);

			//Use this if you want to execute it repeatedly
			int period = 86400000;//24hours
			timer.schedule(new TimeTask(), date, period );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private class TimeTask extends TimerTask
	{
		public void run()
		{
			// insert update 24 hours here
			piService.piListFill();
			updatePiImages();
			System.out.println("updates");//updates
		}
	}

	@RequestMapping(value = "/csv/logins", produces = "text/csv")
	public void exportLogins(HttpServletResponse response) throws IOException {
		List<UserLoginHistory> logins = loginService.getAllLogins();
		CSVExport.exportLogins(response.getWriter(), logins);
	}    

	@RequestMapping(value = "/csv/messages", produces = "text/csv")
	public void exportMessages(HttpServletResponse response) throws IOException {
		List<Messages> messages = messageService.getAllMessages();
		CSVExport.exportMessages(response.getWriter(), messages);
	}

	@RequestMapping(value = "/csv/events", produces = "text/csv")
	public void exportEvents(HttpServletResponse response) throws IOException {
		List<Calendar> events = calendarService.getAllEvents();
		CSVExport.exportEvents(response.getWriter(), events);
	}

	@RequestMapping(value = "/csv/admins", produces = "text/csv")
	public void exportAdmins(HttpServletResponse response) throws IOException {
		List<Admin> admins = adminService.getAllAdmins();
		CSVExport.exportAdmins(response.getWriter(), admins);
	}
}
