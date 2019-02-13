package senior.design.group10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.UserLoginHistoryDAO;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentLoginHistory;
import senior.design.group10.objects.user.UserLoginHistory;
import senior.design.group10.objects.user.Users;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;
import java.util.Optional;

@Service
public class LoginService {
    private final static Logger log = Logger.getLogger(LoginService.class.getName());
    private final UserLoginHistoryDAO userLoginHistoryDAO;
    private final UsersDAO usersDAO;

    @Autowired
    public LoginService(UserLoginHistoryDAO userLoginHistoryDAO, UsersDAO usersDAO) {
        this.userLoginHistoryDAO = userLoginHistoryDAO;
        this.usersDAO = usersDAO;
    }

    public ResponseObject saveNewLogin(SentLoginHistory login){
    	Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());
        
        //find the user attached to the five digit extension
        Optional<Users> usersOptional = usersDAO.findById(login.getBadgeID());
        //checking if the five digit extension is valid
        if(!usersOptional.isPresent())
        	return new ResponseObject(false, "User with extension " + login.getBadgeID() + " cannot be found.");
        
        UserLoginHistory loginToSave = new UserLoginHistory(usersOptional.get(), currentTime);
        log.info("ext: " + login.getBadgeID() + ", time: " + currentTime);
        userLoginHistoryDAO.save(loginToSave);
        return new ResponseObject(true, usersOptional.get().getName());
    }
}
