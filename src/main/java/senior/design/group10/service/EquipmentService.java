package senior.design.group10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senior.design.group10.dao.*;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.EquipmentCheckoutHistory;
import senior.design.group10.objects.response.EquipmentUsageResponse;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentEquipment;
import senior.design.group10.objects.user.Admin;
import senior.design.group10.objects.user.Users;

import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Logger;

/**
 * Service class to handle business logic for equipment to be checked out of the USWRIC
 */
@Service
public class EquipmentService {
    private static final Logger log = Logger.getLogger(Equipment.class.getName());

    private final EquipmentDAO equipmentDAO;
    private final UsersDAO usersDAO;
    private final AdminDAO adminDAO;
    private final EquipmentCheckoutDAO equipmentCheckoutDAO;

    @Autowired
    public EquipmentService(EquipmentDAO equipmentDAO, UsersDAO usersDAO, AdminDAO adminDAO, EquipmentCheckoutDAO equipmentCheckoutDAO){
        this.equipmentDAO = equipmentDAO;
        this.usersDAO = usersDAO;
        this.adminDAO = adminDAO;
        this.equipmentCheckoutDAO = equipmentCheckoutDAO;
    }

    /**
     * Adds a new piece of Equipment to the database. This method will verify that the barcode is not currently
     * affiliated with an existing piece of equipment.
     * @param equipment the Equipment entity to be saved
     * @return {@code true} if equipment is added successfully
     */
    public ResponseObject addNewEquipment(Equipment equipment){
        //verifies that the equipment does not already exist
        if(!equipmentDAO.existsById(equipment.getBarcode())){
            equipment.setInStock(true);
            equipmentDAO.save(equipment);
            return new ResponseObject(true, null);
        }
        //returns if equipment already exists
        return new ResponseObject(false, "Equipment with barcode " + equipment.getBarcode() + " already exists.");
    }

    /**
     * Checks a piece of equipment out from the USWRIC. This method will verify that the equipment exists and that it
     * is not currently checked out.
     * @param sentEquipment the equipment to be checked out
     * @return {@code true} if equipment is successfully checked out.
     */
    public ResponseObject checkout(SentEquipment sentEquipment){
        //Creates a new date object to get exact time of checkout
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        Optional<Users> usersOptional = usersDAO.findById(sentEquipment.getUserExt());
        Optional<Admin> adminOptional = adminDAO.findById(sentEquipment.getAdminExt());
        Optional<Equipment> equipmentOptional = equipmentDAO.findById(sentEquipment.getBarcode());
        //verifies that the user exists
        if(!usersOptional.isPresent()){
            return new ResponseObject(false, "User with extension " + sentEquipment.getUserExt() + " cannot be found");
        }
        //verifies that the administrator exists
        if(!adminOptional.isPresent()){
            return new ResponseObject(false, "Admin with extension " + sentEquipment.getAdminExt() + " cannot be found");
        }
        //verifies that the equipment exists
        if(!equipmentOptional.isPresent()){
            return new ResponseObject(false, "Equipment with barcode " + sentEquipment.getBarcode() + " cannot be found");
        }
        Users user = usersOptional.get();
        Admin admin = adminOptional.get();
        Equipment equipment = equipmentOptional.get();
        //verifies that the equipment is not currently checked out
        if(!equipment.isInStock()){
            return new ResponseObject(false, "Equipment is currently checked out. Please check equipment in before checking out again.");
        }

        //creates a new EquipmentCheckoutHistory entity to be saved to the database
        EquipmentCheckoutHistory history = new EquipmentCheckoutHistory(equipment, user, currentTime, admin);

        equipmentCheckoutDAO.save(history);

        //sets the inStock attribute to false for the given equipment to represent that it is currently checked out
        equipment.setInStock(false);
        equipmentDAO.save(equipment);

        return new ResponseObject(true, null);
    }

    /**
     * Checks a piece of equipment back into the USWRIC. This method will verify that the barcode exists and that the
     * equipment is currently checked out.
     * @param barcode String representing the barcode of the equipment to checkin.
     * @return {@code true} if equipment is successfully checked in.
     */
    public ResponseObject checkin(String barcode){
        //Creates a new date object to get exact time of checkin
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        //verifies that the equipment exists
        Optional<Equipment> equipmentOptional = equipmentDAO.findById(barcode);
        if(!equipmentOptional.isPresent()){
            return new ResponseObject(false, "Equipment with barcode " + barcode + " cannot be found");
        }
        //checks to see if the equipment object exists in the equipment_checkout table
        Equipment equipmentToFind = equipmentOptional.get();
        List<EquipmentCheckoutHistory> historyList = equipmentCheckoutDAO.findByEquipment(equipmentToFind);
        if(historyList == null){
            return new ResponseObject(false, "Equipment with barcode " + barcode + " has not ever been checked out");
        }
        //verifies that the equipment is currently checked out
        if(equipmentToFind.isInStock()){
            return new ResponseObject(false, "Equipment with barcode " + barcode + " is not currently checked out");
        }
        //sets the inStock attribute to true to represent the equipment is currently available
        equipmentToFind.setInStock(true);
        equipmentDAO.save(equipmentToFind);

        //sets the checkin time for the EquipmentCheckoutHistory entity.
        /* uses a Comparator to return the most recent EquipmentCheckoutHistory entity, then sets the check in time on
        *  the most recent entity.
        */
        Comparator<EquipmentCheckoutHistory> cmp = Comparator.comparing(EquipmentCheckoutHistory::getCheckoutDate);
        EquipmentCheckoutHistory recent = Collections.max(historyList, cmp);

        recent.setCheckinDate(currentTime);
        equipmentCheckoutDAO.save(recent);
        return new ResponseObject(true, null);
    }

    public List<EquipmentUsageResponse> getUsageStatistics(){
        return equipmentDAO.getEquipmentUsage();
    }
}