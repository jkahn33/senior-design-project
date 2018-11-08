package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.AdminService;
import senior.design.group10.service.UserService;

import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.sent.SentLoginHistory;
import senior.design.group10.service.LoginService;
import senior.design.group10.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/android")
public class AndroidController {

    private final
    UserService userService;
    private final
    AdminService adminService;
    private final
    LoginService loginService;

    @Autowired
    public AndroidController(UserService userService, AdminService adminService, LoginService loginService) {
        this.userService = userService;
        this.adminService = adminService;
        this.loginService = loginService;
    }
    
    //API endpoint for creating a new user.
    @GetMapping("/newUser")
    @ResponseBody
    public ResponseObject newUser(SentUser sentUser){
    	sentUser = new SentUser("name", "11111", "0123");
        return userService.saveNewUser(sentUser);
    }

    @PostMapping("/validateAdmin")
    @ResponseBody
    public boolean validateAdmin(AdminInQuestion adminInQuestion){
        return adminService.isAdminValid(adminInQuestion);
    }
    @GetMapping("/storeLogin")
    @ResponseBody
    public ResponseObject storeLogin(SentLoginHistory login) {
    	login = new SentLoginHistory("11111");
    	return loginService.saveNewLogin(login);
    }
    //Just for testing. UserService.getAllUsers() returns a list of all users
    @GetMapping("/printAllUsers")
    @ResponseBody
    public void printAllUsers() {
    	System.out.println(userService.getAllUsers());
    }
}
