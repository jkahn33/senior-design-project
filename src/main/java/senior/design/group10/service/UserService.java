package senior.design.group10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.UsersStatisticResponse;
import senior.design.group10.objects.sent.SentUser;
import senior.design.group10.objects.sent.StatisticsRequest;
import senior.design.group10.objects.user.UserLoginHistory;
import senior.design.group10.objects.user.Users;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service class to handle business logic for Users
 */
@Service
public class UserService {
    private final UsersDAO usersDAO;

    @Autowired
    public UserService(UsersDAO usersDAO)
    {
        this.usersDAO = usersDAO;
    }

    /**
     * This method will check to make sure the 5 digit extension does not already exist in the database and if it doesn't,
     * save the new user object to the database.
     * @param sentUser a SentUser object which contains name, extension, and department code.
     * @return a ResponseObject with a success boolean value and an error message if necessary.
     */
    public ResponseObject saveNewUser(SentUser sentUser){
        //Creates a new date object to get exact time of creation of user
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        //checks to make sure the extension has not been used before.
        //If the extension already exists, a message will be sent to the client
        if(usersDAO.existsById(sentUser.getExt())){
            return new ResponseObject(false, "A user with extension " + sentUser.getExt() + " already exists.");
        }

        //creates a new Users object which is the database entity. This object has the user data sent from the front end
        //as well as the datetime object created above.
        Users userToSave = new Users(sentUser.getName(), currentTime, sentUser.getExt(), sentUser.getDep());

        //accesses the DAO class and uses Spring's CrudRepository class to save the new user.
        usersDAO.save(userToSave);
        return new ResponseObject(true, null);
    }
    
	/**Method to get a list of all users in the database.
	 * This method returns a List of all users.
	 * @return a list of all Users objects
	 */
    public List<Users> getAllUsers() {
    	return (List<Users>)usersDAO.findAll();
    }

    /**
     * This method is used to query user logins between two different datetimes
     * @param request The two Timestamp objects to search between
     * @return a list of all users' name, badge_id, and time of login who logged into the USWRIC between the query times
     */
    public List<UsersStatisticResponse> getUsersBetweenDates(StatisticsRequest request){
        Timestamp start = Timestamp.valueOf(request.getStart());
        Timestamp end = Timestamp.valueOf(request.getEnd());

        return usersDAO.getUsersBetweenDates(start, end);
    }

    /**
     * Finds a specific user by the given badge ID.
     * @param id The badge ID to identify the user.
     * @return An object containing the user if it exists and null otherwise.
     */
    public Users getUserById(String id){
        Optional<Users> usersOptional = usersDAO.findById(id);
        return usersOptional.orElse(null);
    }

    /**
     * Removes a user by the given badge ID.
     * <br>TODO: The method currently removes completely from the system but in the future it should only remove it from
     *  the active table and the entity should remain in another table for all users that ever were in the system
     * @param id The badge ID of the user to remove from the system.
     * @return a ResponseObject with a success boolean value and an error message if necessary.
     */
    public ResponseObject removeUser(String id){
        Optional<Users> usersOptional = usersDAO.findById(id);
        if(!usersOptional.isPresent()){
            return new ResponseObject(false, "User with badge ID " + id + " does not exist.");
        }
        usersDAO.delete(usersOptional.get());
        return new ResponseObject(true, null);
    }
}
