package senior.design.group10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.response.NewUserResponse;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.user.Users;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class UserService {
    private final static Logger log = Logger.getLogger(UserService.class.getName());
    private final UsersDAO usersDAO;

    @Autowired
    public UserService(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public NewUserResponse saveNewUser(SentUser sentUser){
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        //checks to make sure the extension is valid
        

        Users userToSave = new Users(sentUser.getName(), currentTime, sentUser.getExt(), sentUser.getDep());
        log.info("name: " + sentUser.getName() + ", ext: " + sentUser.getExt() + ", dep: " + sentUser.getDep());
        usersDAO.save(userToSave);
        return new NewUserResponse(true, null);
    }
}
