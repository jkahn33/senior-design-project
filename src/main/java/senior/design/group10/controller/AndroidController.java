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
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.EquipmentService;
import senior.design.group10.service.UserService;
import senior.design.group10.service.PrinterService;

@Controller
@RequestMapping("/android")
public class AndroidController {

	private final
	UserService userService;
	private final
	AdminService adminService;
	private final
	EquipmentService equipmentService;
	private final
	PrinterService printerService;

	@Autowired
	public AndroidController(UserService userService, AdminService adminService, EquipmentService equipmentService, PrinterService printerService) {
		this.userService = userService;
		this.adminService = adminService;
		this.equipmentService = equipmentService;
		this.printerService = printerService;
	}

	//API endpoint for creating a new user.
	@GetMapping("/newUser")
	@ResponseBody
	public ResponseObject newUser(SentUser sentUser)
	{
		SentUser testing = new SentUser("pat","1234","123");
		return userService.saveNewUser(testing);
	}

	@PostMapping("/validateAdmin")
	@ResponseBody
	public boolean validateAdmin(AdminInQuestion adminInQuestion){
		return adminService.isAdminValid(adminInQuestion);
	}

	@GetMapping("/newEquipment")
	@ResponseBody
	public ResponseObject newEquipment(Equipment equipment){
		equipment = new Equipment("thisisabarcode", "Raspberry Pi", true);
		return equipmentService.addNewEquipment(equipment);
	}


	@GetMapping("/checkoutEquipment")
	@ResponseBody
	public ResponseObject checkoutEquipment(SentEquipment equipment){
		equipment = new SentEquipment("thisisabarcode", "11111", "12345");
		return equipmentService.checkout(equipment);
	}

	@GetMapping("/newPrinterReservation")
	@ResponseBody
	public ResponseObject newPrinterReservation(SentPrinterReservation printerReservation)
	{
		Timestamp timestamp = java.sql.Timestamp.valueOf("2007-09-23 10:10:10");
	
		printerReservation = new SentPrinterReservation("1234","print boat today",timestamp,"24:00","THis is the additional comment","A");

		
		return printerService.addPrintRes(printerReservation);
	}

}
