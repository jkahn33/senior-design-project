package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.EquipmentWrapper;
import senior.design.group10.objects.sent.SentEquipment;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.EquipmentService;
import senior.design.group10.service.UserService;

import java.util.logging.Logger;

@Controller
@RequestMapping("/android")
public class AndroidController {
    private final static Logger log = Logger.getLogger(AndroidController.class.getName());

    private final
    UserService userService;
    private final
    AdminService adminService;
    private final
    EquipmentService equipmentService;

    @Autowired
    public AndroidController(UserService userService, AdminService adminService, EquipmentService equipmentService) {
        this.userService = userService;
        this.adminService = adminService;
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
    public ResponseObject newEquipment(@RequestBody Equipment equipment){
        return equipmentService.addNewEquipment(equipment);
    }

    @PostMapping("/checkoutEquipment")
    @ResponseBody
    public ResponseObject checkoutEquipment(@RequestBody SentEquipment equipment){
        return equipmentService.checkout(equipment);
    }
    @PostMapping("/checkinEquipment")
    @ResponseBody
    public ResponseObject checkinEquipment(EquipmentWrapper barcode){
        return equipmentService.checkin(barcode.getBarcode());
    }
}
