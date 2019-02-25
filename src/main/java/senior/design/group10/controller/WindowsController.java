package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.ReturnAdmin;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.EditAdmin;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.objects.sent.SentPi;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.PiService;

@Controller
@RequestMapping("/windows")
public class WindowsController {

    private final AdminService adminService;
    private final PiService piService;

    @Autowired
    public WindowsController(AdminService adminService, PiService piService) {
        this.adminService = adminService;
        this.piService = piService;
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
    
    
    //Checks for duplicate ip then stores to ip table
    //if duplicate returns false object response
    //else stores in db and will be used to connect to different pis
    @PostMapping("addPi")
    @ResponseBody
    public ResponseObject addPi(@RequestBody SentPi sentPi)
    {
        return piService.addPi(sentPi);        
    }
    
    @GetMapping("/execComToPi")
    @ResponseBody
    public ResponseObject sshPi()
    {
    		//PiService piservice = new PiService();
    		return new ResponseObject(true, null);
    }
}
