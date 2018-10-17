package senior.design.group10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import senior.design.group10.objects.UserCredentials;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/android")
public class AndroidController {

    @PostMapping("/entranceCredentials")
    public void entranceCredentials(@RequestBody UserCredentials credentials){

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
