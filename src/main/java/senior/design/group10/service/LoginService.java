package senior.design.group10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.UserLoginHistoryDAO;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.response.NewUserResponse;
import senior.design.group10.objects.sent.SentLoginHistory;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.user.UserLoginHistory;
import senior.design.group10.objects.user.Users;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class LoginService {
    private final static Logger log = Logger.getLogger(LoginService.class.getName());
    private final UserLoginHistoryDAO userLoginHistoryDAO;

    @Autowired
    public LoginService(UserLoginHistoryDAO userLoginHistoryDAO) {
        this.userLoginHistoryDAO = userLoginHistoryDAO;
    }

    public NewUserResponse saveNewLogin(SentLoginHistory login){
    	Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        //checks to make sure the extension is valid
        

        UserLoginHistory loginToSave = new UserLoginHistory(login.getExt(), currentTime);
        log.info("ext: " + login.getExt() + ", time: " + currentTime);
        userLoginHistoryDAO.save(loginToSave);
        return new NewUserResponse(true, null);
    }
}
