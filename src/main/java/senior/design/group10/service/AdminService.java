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
import java.util.Optional;
import java.util.logging.Logger;

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
        if(!adminOptional.isPresent() || !activeAdminDAO.existsById(editAdmin.getOldBadgeID())){
            return new ResponseObject(false, "Admin cannot be found");
        }
        Admin adminToEdit = adminOptional.get();
        if(editAdmin.getBadgeID() != null){
            adminToEdit.setBadgeID(editAdmin.getBadgeID());
        }
        if(editAdmin.getName() != null){
            adminToEdit.setName(editAdmin.getName());
        }
        if(editAdmin.getNewPass() != null){
            adminToEdit.setPassword(passwordEncoder.encode(editAdmin.getNewPass()));
        }
        adminDAO.save(adminToEdit);
        return new ResponseObject(true, null);
    }
    
    public ReturnAdmin getAdminById(String id){
        Optional<Admin> adminOptional = adminDAO.findById(id);
        if(adminOptional.isPresent() && activeAdminDAO.existsById(id)){
            Admin admin = adminOptional.get();
            return new ReturnAdmin(admin.getName(), admin.getBadgeID());
        }
        return null;
    }
}
