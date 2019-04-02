package senior.design.group10.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.ReturnAdmin;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.EditAdmin;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.objects.tv.Calendar;
import senior.design.group10.objects.tv.Messages;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.BreakoutService;
import senior.design.group10.service.CSVExport;
import senior.design.group10.service.CalendarService;
import senior.design.group10.service.EquipmentService;
import senior.design.group10.service.LoginService;
import senior.design.group10.service.MessageService;
import senior.design.group10.service.PrinterService;
import senior.design.group10.service.UserService;
import senior.design.group10.objects.user.Admin;
import senior.design.group10.objects.user.UserLoginHistory;
import senior.design.group10.objects.user.Users;

@Controller
@RequestMapping("/windows")
public class WindowsController {

    private final AdminService adminService;
    private final UserService userService;
    private final BreakoutService breakoutService;
    private final EquipmentService equipmentService;
    private final PrinterService printerService;
    private final LoginService loginService;
    private final MessageService messageService;
    private final CalendarService calendarService;

    @Autowired
    public WindowsController(AdminService adminService, 
    						UserService userService, 
    						BreakoutService breakoutService,
    						EquipmentService equipmentService,
    						PrinterService printerService,
    						LoginService loginService,
    						MessageService messageService,
    						CalendarService calendarService) {
        this.adminService = adminService;
        this.userService = userService;
        this.breakoutService = breakoutService;
        this.equipmentService = equipmentService;
        this.printerService = printerService;
        this.loginService = loginService;
        this.messageService = messageService;
        this.calendarService = calendarService;
    }

    @PostMapping("/newAdmin")
    @ResponseBody
    public ResponseObject newAdmin(@RequestBody NewAdmin sentAdmin){
	return adminService.createNewAdmin(sentAdmin);
    }

    @PostMapping("/validateAdmin")
    @ResponseBody
    public boolean validateAdmin(@RequestBody AdminInQuestion adminInQuestion){
        return adminService.isAdminValid(adminInQuestion);
    }

    @PostMapping("/getAdmin")
    @ResponseBody
    public ReturnAdmin getAdmin(@RequestBody EditAdmin editAdmin){
        return adminService.getAdminById(editAdmin.getOldBadgeID());
    }

    @PostMapping("/editAdmin")
    @ResponseBody
    public ResponseObject editAdmin(@RequestBody EditAdmin editAdmin){
        return adminService.editAdmin(editAdmin);
    }
    
    @RequestMapping(value = "/csv/users", produces = "text/csv")
    public void exportUsers(HttpServletResponse response) throws IOException {
    	List<Users> users = userService.getAllUsers();
    	CSVExport.exportUsers(response.getWriter(), users);
    }
    
    @RequestMapping(value = "/csv/breakoutres", produces = "text/csv")
    public void exportBreakoutRes(HttpServletResponse response) throws IOException {
    	List<BreakoutReservations> res = breakoutService.getAllRes();
    	CSVExport.exportBreakoutRes(response.getWriter(), res);
    }
    
    @RequestMapping(value = "/csv/equipment", produces = "text/csv")
    public void exportEquipment(HttpServletResponse response) throws IOException {
    	List<Equipment> equipment = equipmentService.getAllEquipment();
    	CSVExport.exportEquipment(response.getWriter(), equipment);
    }
    
    @RequestMapping(value = "/csv/printerres", produces = "text/csv")
    public void exportPrinterRes(HttpServletResponse response) throws IOException {
    	List<PrinterReservations> res = printerService.getAllRes();
    	CSVExport.exportPrinterRes(response.getWriter(), res);
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
