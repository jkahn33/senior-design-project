package senior.design.group10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.ActiveAdminDAO;
import senior.design.group10.dao.AdminDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.response.ReturnAdmin;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.EditAdmin;
import senior.design.group10.objects.sent.NewAdmin;
import senior.design.group10.objects.user.ActiveAdmin;
import senior.design.group10.objects.user.Admin;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service class to handle business logic for administrators
 */
@Service
public class AdminService {
    private final
    AdminDAO adminDAO;
    private final
    ActiveAdminDAO activeAdminDAO;
    private final
    PasswordEncoder passwordEncoder;

    private static final Logger log = Logger.getLogger(AdminService.class.getName());

    @Autowired
    public AdminService(AdminDAO adminDAO, ActiveAdminDAO activeAdminDAO, PasswordEncoder passwordEncoder) {
        this.adminDAO = adminDAO;
        this.activeAdminDAO = activeAdminDAO;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Checks to make sure the 5 digit extension does not already exist in the database and if it doesn't,
     * saves the new admin object to the database. Additionally, the admin password will be hashed before saving.
     * @param sentAdmin NewAdmin object containing name, extension, and password.
     * @return {@code ResponseObject} containing a success boolean and an error message if necessary.
     */
    public ResponseObject createNewAdmin(NewAdmin sentAdmin){
        //creates a new Date object to save the current time of creation
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        //checks to make sure the extension has not been used before.
        //If the extension already exists, a message will be sent to the client
        if(adminDAO.existsById(sentAdmin.getBadgeID()) || activeAdminDAO.existsById(sentAdmin.getBadgeID())){
            return new ResponseObject(false, "An admin with extension " + sentAdmin.getBadgeID() + " already exists.");
        }

        //creates a new Admin database entity and saves it
        //passwordEncode.encode() hashes the password before saving it to the database
        Admin adminToSave = new Admin(sentAdmin.getBadgeID(), passwordEncoder.encode(sentAdmin.getPassword()), sentAdmin.getName(), currentTime);
        ActiveAdmin activeAdminToSave = new ActiveAdmin(sentAdmin.getName(), sentAdmin.getBadgeID());
        
        //saves the admin object
        adminDAO.save(adminToSave);
        activeAdminDAO.save(activeAdminToSave);
        return new ResponseObject(true, null);
    }

    /**
     * Validate an admin's password against the given extension.
     * @param adminInQuestion An object containing just an extension and password.
     * @return {@code true} if extension exists and password matches
     */
    public boolean isAdminValid(AdminInQuestion adminInQuestion){
        //checks to make sure the admin even exists by the ext. If it does, checks to make sure password matches.
        Optional<Admin> adminOptional = adminDAO.findById(adminInQuestion.getBadgeID());
    	if(adminOptional.isPresent() && activeAdminDAO.existsById(adminInQuestion.getBadgeID())){
        	//fetch permanent admin account (contains password)
            Admin admin = adminOptional.get();
            return passwordEncoder.matches(adminInQuestion.getPassword(), admin.getPassword());
        }
        return false;
    }

    /**
     * Edit an admin's information based on the provided fields.
     * @param editAdmin An object containing the desired information to edit.
     * @return {@code true} if extension exists and password matches
     */
    public ResponseObject editAdmin(EditAdmin editAdmin){
        Optional<Admin> adminOptional = adminDAO.findById(editAdmin.getOldBadgeID());
        Optional<ActiveAdmin> activeAdminOptional = activeAdminDAO.findById(editAdmin.getOldBadgeID());
        if(!adminOptional.isPresent() || !activeAdminOptional.isPresent()){
            return new ResponseObject(false, "Admin cannot be found");
        }
        Admin adminToEdit = adminOptional.get();
        ActiveAdmin activeAdminToEdit = activeAdminOptional.get();
        //we delete the current entry in the table in case there's a new badge ID
        adminDAO.delete(adminToEdit);	
        activeAdminDAO.delete(activeAdminToEdit);
        if(editAdmin.getBadgeID() != null){
            if(adminDAO.findById(editAdmin.getBadgeID()).isPresent()) {
        		activeAdminDAO.save(activeAdminToEdit);
        		adminDAO.save(adminToEdit);
            	return new ResponseObject(false, "Another admin with Badge ID " + editAdmin.getBadgeID() + " already exists.");
            }
        	adminToEdit.setBadgeID(editAdmin.getBadgeID());
            activeAdminToEdit.setBadgeID(editAdmin.getBadgeID());
        }
        if(editAdmin.getName() != null){
            adminToEdit.setName(editAdmin.getName());
            activeAdminToEdit.setName(editAdmin.getName());
        }
        //do not need to verify correctness of old password as that was already done that before the request was sent
        if(editAdmin.getNewPass() != null){
            adminToEdit.setPassword(passwordEncoder.encode(editAdmin.getNewPass()));
        }
		activeAdminDAO.save(activeAdminToEdit);
		adminDAO.save(adminToEdit);
		return new ResponseObject(true, null);
    }
    
	/**
	 * Returns the administrator located by the given badge id
	 * @param id the badge id to search by
	 * @return the ReturnAdmin object which contains the name and badge_id
	 */
    public ReturnAdmin getAdminById(String id) {
        Optional<Admin> adminOptional = adminDAO.findById(id);
        if(adminOptional.isPresent() && activeAdminDAO.existsById(id)){
            Admin admin = adminOptional.get();
            return new ReturnAdmin(admin.getName(), admin.getBadgeID());
        }
        return null;
    }

    /**
     * Returns an administrator's name by id
     * @param id the badge id to search by
     * @return the name of the account owner associated with the id
     */
    public ResponseObject getAdminName(String id) {
        Optional<Admin> adminOptional = adminDAO.findById(id);
        if(adminOptional.isPresent()){
            return new ResponseObject(true, adminOptional.get().getName());
        }
        return new ResponseObject(false, null);
    }

    /**
     * Removes a user by the given badge ID.
     * <br>TODO: The method currently removes completely from the system but in the future it should only remove it from
     *  the active admin table and the entity should remain in another table for all users that ever were in the system
     * @param id The badge ID of the user to remove from the system.
     * @return a ResponseObject with a success boolean value and an error message if necessary.
     */
    public ResponseObject removeAdmin(String id){
        Optional<Admin> adminOptional = adminDAO.findById(id);
        if(!adminOptional.isPresent()){
            return new ResponseObject(false, "Admin with badge ID " + id + " does not exist.");
        }
        adminDAO.delete(adminOptional.get());
        return new ResponseObject(true, null);
    }
    
    public List<Admin> getAllAdmins() {
    	return (List<Admin>)adminDAO.findAll();
    }
}
