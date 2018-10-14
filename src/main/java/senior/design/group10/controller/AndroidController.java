package senior.design.group10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/android")
public class AndroidController {

    @PostMapping("/entranceCredentials")
    public void entranceCredentials(HttpServletRequest request){

    }
    @PostMapping("/printerCredentials")
    public void printerCredentials(HttpServletRequest request){

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
