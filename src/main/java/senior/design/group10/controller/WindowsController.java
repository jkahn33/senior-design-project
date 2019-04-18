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
import senior.design.group10.objects.user.DebugUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.MessageService;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.PrinterUsageUsers;
import senior.design.group10.objects.response.*;
import senior.design.group10.objects.sent.*;
import senior.design.group10.objects.user.Users;
import senior.design.group10.service.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    @Autowired
    public WindowsController(AdminService adminService,
                             UserService userService,
                             EquipmentService equipmentService,
                             MessageService messageService,
                             PrinterService printerService,
							  PiService piService,
							  BreakoutService breakoutService,
                             FutureService futureService) {
        this.adminService = adminService;
        this.userService = userService;
        this.equipmentService = equipmentService;
        this.messageService = messageService;
        this.printerService = printerService;
        this.piService = piService;
        this.breakoutService = breakoutService;
        this.futureService = futureService;
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

    @GetMapping("/debug")
    @ResponseBody
    public DebugUser debugUser(){
        return userService.debugUser();
    }

	////////////////////////////////////////////////
	//Pi commands
	//
	////////////////////////////////////////////////

	//Checks for duplicate ip then stores to ip table
	//if duplicate returns false object response
	//else stores in db and will be used to connect to different pis
	@GetMapping("/addPi")
	@ResponseBody
	public ResponseObject addPi(/*@RequestBody SentPi sentPi*/)
	{
	    SentPi sentPi = new SentPi("192.168.1.2", "pi", "admin");
		return piService.addPi(sentPi);        
	}

	
	@GetMapping("/fillPiList")
	public void fillPiList()
	{
		//piService.createPiImageFolder("PiImages");
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
    //@GetMapping("newMessage") //for testing
    @ResponseBody
    public ResponseObject newMessage(@RequestBody SentMessage sentMessage) 
    //public ResponseObject newMessage(SentMessage sentMessage) This is for testing
    {
        //Sending a message
        //Used for making new messages
        //sentMessage = new SentMessage("entering past message", "12345", "2010-01-01 01:01:01");
        return messageService.createNewMessage(sentMessage);
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
    		System.out.println("Getting Current Messages");
    		piService.renderMessagesImage(messageService.getCurrentMessages());
    		System.out.println("Getting Current Breakout Reservation");
    		//Todo
    		//Add future events function

    		piService.renderBreakoutImage(breakoutService.todaysReservations());
    		piService.renderFutureImage(futureService.getFutureMessages());
    		//Send the folder to the pi
    		piService.piListFill();

    		System.out.println("Sending the Images");

    		
    		piService.copyFolderToPi("PiImages", "Pictures/Slides");

        return  new ResponseObject(true,null);
    }
    
    @GetMapping("/runSlideShow")
    @ResponseBody
    public ResponseObject startSlideshow()
    {
    		piService.startSlideShow();
    		return new ResponseObject(true,null);
    }
    @GetMapping("/printerUsage")
    @ResponseBody
    public List<PrinterUsageResponse> getPrinterUsage(){
        return printerService.getPrinterUsage();
    }

    @GetMapping("/printerUserUsage")
    @ResponseBody
    public List<PrinterUsageUsers> getPrinterUserUsage(){
        return printerService.getUserUsage();
    }
}
