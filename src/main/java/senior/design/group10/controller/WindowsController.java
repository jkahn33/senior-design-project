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
import senior.design.group10.service.AdminService;
import senior.design.group10.service.MessageService;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.response.*;
import senior.design.group10.objects.sent.*;
import senior.design.group10.objects.user.Users;
import senior.design.group10.service.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Logger;
import senior.design.group10.service.PiService;

@Controller
@RequestMapping("/windows")
public class WindowsController {
	private final static Logger log = Logger.getLogger(WindowsController.class.getName());

	private final AdminService adminService;
	private final PiService piService;
	private final UserService userService;
	private final EquipmentService equipmentService;
	private final MessageService messageService;

	@Autowired
	public WindowsController(AdminService adminService, UserService userService, EquipmentService equipmentService, MessageService messageService, PiService piService) {
		this.adminService = adminService;
		this.userService = userService;
		this.equipmentService = equipmentService;
		this.messageService = messageService;
		this.piService = piService;
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

	@GetMapping
	@ResponseBody
	public void getSpecificUserStatistics(){
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
	///////////////////////////////////////////////
	//Photo commands
	//
	////////////////////////////////////////////////


	@GetMapping("/renderImage")
	@ResponseBody
	public boolean renderImage() {
		return messageService.renderImage();
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
	public ResponseObject addPi(@RequestBody SentPi sentPi)
	{
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
        return messageService.createNewMessage(sentMessage);
    }    
    
    /*
     * Returns all messages with data greater than or equal to current date.
     * Messages contain:
     * -a string (the message)
     * -a timestamp of creation (automatically generated by the backend)
     * -an associated administrator account
     */
    @GetMapping("/getCurrentMessages")
    @ResponseBody
    public ResponseObject getCurrentMessages(@RequestBody SentMessage sentMessage) {
    		messageService.getCurrentMessages();
        return  new ResponseObject(true,null);
    }

}
