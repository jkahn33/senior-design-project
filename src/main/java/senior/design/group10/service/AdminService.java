package senior.design.group10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import senior.design.group10.dao.AdminDAO;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.AdminInQuestion;
import senior.design.group10.objects.sent.NewAdmin;
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
    PasswordEncoder passwordEncoder;

    private static final Logger log = Logger.getLogger(AdminService.class.getName());

    @Autowired
    public AdminService(AdminDAO adminDAO, PasswordEncoder passwordEncoder) {
        this.adminDAO = adminDAO;
        this.passwordEncoder = passwordEncoder;
    }

    /**Method to save new admins.
     * This method will check to make sure the 5 digit extension does not already exist in the database and if it doesn't,
     * save the new admin object to the database. Additionally, the admin password will be hashed before saving.
     */
    public ResponseObject createNewAdmin(NewAdmin sentAdmin){
        //creates a new Date object to save the current time of creation
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        //checks to make sure the extension has not been used before.
        //If the extension already exists, a message will be sent to the client
        if(adminDAO.existsById(sentAdmin.getExt())){
            return new ResponseObject(false, "An admin with extension " + sentAdmin.getExt() + " already exists.");
        }

        //creates a new Admin database entity and saves it
        //passwordEncode.encode() hashes the password before saving it to the database
        Admin adminToSave = new Admin(sentAdmin.getExt(), passwordEncoder.encode(sentAdmin.getPassword()), sentAdmin.getName(), currentTime);

        //saves the admin object
        adminDAO.save(adminToSave);
        return new ResponseObject(true, null);
    }
    public boolean validate(AdminInQuestion adminInQuestion){
        Optional<Admin> adminOptional = adminDAO.findById(adminInQuestion.getExt());
        if(adminOptional.isPresent()){
            Admin admin = adminOptional.get();
            return passwordEncoder.matches(adminInQuestion.getPassword(), admin.getPassword());
        }
        return false;
    }
}
