package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import senior.design.group10.objects.response.NewUserResponse;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.sent.SentLoginHistory;
import senior.design.group10.service.LoginService;
import senior.design.group10.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/android")
public class AndroidController {

    private final
    UserService userService;
    LoginService loginService;

    @Autowired
    public AndroidController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }
    
    //API endpoint for creating a new user.
    @PostMapping("/newUser")
    @ResponseBody
    public NewUserResponse newUser(SentUser sentUser){
        return userService.saveNewUser(sentUser);
    }

    @PostMapping("/registerUser")
    public void registerUser(/*@RequestBody User user*/){

    }

    @PostMapping("/schedulePrint")
    public void schedulePrint(HttpServletRequest request){

    }
    @PostMapping("/getPrintTimes")
    public void getPrintTimes(HttpServletRequest request){

    }
    @PostMapping("/getUserJobs")
    public void getUserJobs(HttpServletRequest request){

    }
    @GetMapping("/storeLogin")
    public NewUserResponse storeLogin(SentLoginHistory login) {
    	login = new SentLoginHistory("55555");
    	return loginService.saveNewLogin(login);
    }
}
