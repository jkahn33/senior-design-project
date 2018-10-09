package senior.design.group10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import senior.design.group10.objects.TestWrap;

import java.util.logging.Logger;

@Controller
public class WindowsController {

    private final static Logger log = Logger.getLogger(WindowsController.class.getName());

    @GetMapping("/")
    @ResponseBody
    public TestWrap homePage(){
        return new TestWrap("Hello, world!");
    }
}
