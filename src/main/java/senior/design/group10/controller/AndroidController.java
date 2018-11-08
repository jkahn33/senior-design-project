package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.SentEquipment;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.EquipmentService;
import senior.design.group10.service.UserService;

@Controller
@RequestMapping("/android")
public class AndroidController {

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
    public ResponseObject newUser(SentUser sentUser){
        return userService.saveNewUser(sentUser);
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
    @GetMapping("/checkinEquipment")
    @ResponseBody
    public ResponseObject checkinEquipment(String barcode){
        barcode = "thisisabarcode";
        return equipmentService.checkin(barcode);
    }
}
