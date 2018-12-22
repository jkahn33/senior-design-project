package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.response.*;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.EditAdmin;
import senior.design.group10.objects.sent.StatisticsRequest;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.objects.user.Users;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.EquipmentService;
import senior.design.group10.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/windows")
public class WindowsController {

    private final AdminService adminService;
    private final UserService userService;
    private final EquipmentService equipmentService;

    @Autowired
    public WindowsController(AdminService adminService, UserService userService, EquipmentService equipmentService) {
        this.adminService = adminService;
        this.userService = userService;
        this.equipmentService = equipmentService;
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

    @PostMapping("/userStatistics")
    @ResponseBody
    public List<UsersStatisticResponse> getUserStatistics(@RequestBody StatisticsRequest request){
        //StatisticsRequest request = new StatisticsRequest("2018-12-04 00:00:00", "2018-12-18 11:59:59");
        return userService.getUsersBetweenDates(request);
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
}
