package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.service.AdminService;

@Controller
@RequestMapping("/windows")
public class WindowsController {

    private final
    AdminService adminService;

    @Autowired
    public WindowsController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/newAdmin")
    @ResponseBody
    public ResponseObject newAdmin(NewAdmin sentAdmin){
        return adminService.createNewAdmin(sentAdmin);
    }

    @GetMapping("/validateAdmin")
    @ResponseBody
    public boolean validateAdmin(AdminInQuestion adminInQuestion){
        return adminService.isAdminValid(adminInQuestion);
    }
}
