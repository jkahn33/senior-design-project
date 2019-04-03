package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    public WindowsController(AdminService adminService,
                             UserService userService,
                             EquipmentService equipmentService,
                             MessageService messageService,
                             PrinterService printerService) {
        this.adminService = adminService;
        this.userService = userService;
        this.equipmentService = equipmentService;
        this.messageService = messageService;
        this.printerService = printerService;
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

    @GetMapping("/renderImage")
    @ResponseBody
    public boolean renderImage() {
        return messageService.renderImage();
    }

    @GetMapping("/execComToPi")
    @ResponseBody
    public ResponseObject sshPi()
    {
    		PiService piservice = new PiService();
    		return new ResponseObject(true, null);
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
