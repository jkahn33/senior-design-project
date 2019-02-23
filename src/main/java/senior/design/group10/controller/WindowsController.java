package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.ReturnAdmin;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.EditAdmin;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.PiService;

@Controller
@RequestMapping("/windows")
public class WindowsController {

    private final AdminService adminService;

    @Autowired
    public WindowsController(AdminService adminService) {
        this.adminService = adminService;
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
    
    @GetMapping("/execComToPi")
    @ResponseBody
    public ResponseObject sshPi()
    {
    		PiService piservice = new PiService();
    		//piservice.ExecComToPi("ls");
    		return new ResponseObject(true, null);
    }
}
