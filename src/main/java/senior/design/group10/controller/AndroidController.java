package senior.design.group10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/android")
public class AndroidController {

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
