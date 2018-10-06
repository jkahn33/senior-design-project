package senior.design.group10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class WindowsController {

    private final static Logger log = Logger.getLogger(WindowsController.class.getName());

    @GetMapping("/")
    public String homePage(){
        return "home";
    }
}
