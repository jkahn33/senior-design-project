package senior.design.group10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import senior.design.group10.dao.HibernateTest;
import senior.design.group10.dao.TestWrapDAO;
import senior.design.group10.objects.TestWrap;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/windows")
public class WindowsController {
    @Autowired
    TestWrapDAO testWrapDAO;
    @Autowired
    HibernateTest hibernateTest;

    private final static Logger log = Logger.getLogger(WindowsController.class.getName());

    @GetMapping("/test")
    @ResponseBody
    public TestWrap homePage(){
        log.info("INSIDE METHOD");
        List<TestWrap> wraps = hibernateTest.getUserDetails();
        return wraps.get(0);
    }

}
