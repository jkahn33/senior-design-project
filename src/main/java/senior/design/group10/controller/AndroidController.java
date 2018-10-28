package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import senior.design.group10.objects.response.NewUserResponse;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/android")
public class AndroidController {

    private final
    UserService userService;

    @Autowired
    public AndroidController(UserService userService) {
        this.userService = userService;
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
}
