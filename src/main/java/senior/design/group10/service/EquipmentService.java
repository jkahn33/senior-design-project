package senior.design.group10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senior.design.group10.dao.*;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.EquipmentCheckoutHistory;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentEquipment;
import senior.design.group10.objects.user.Admin;
import senior.design.group10.objects.user.Users;

import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Logger;

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

    public ResponseObject addNewEquipment(Equipment equipment){
        if(!equipmentDAO.existsById(equipment.getBarcode())){
            equipmentDAO.save(equipment);
            return new ResponseObject(true, null);
        }
        return new ResponseObject(false, "Equipment with barcode " + equipment.getBarcode() + " already exists.");
    }
    public ResponseObject checkout(SentEquipment sentEquipment){
        //Creates a new date object to get exact time of creation of user
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        Optional<Users> usersOptional = usersDAO.findById(sentEquipment.getUserExt());
        Optional<Admin> adminOptional = adminDAO.findById(sentEquipment.getAdminExt());
        Optional<Equipment> equipmentOptional = equipmentDAO.findById(sentEquipment.getBarcode());
        if(!usersOptional.isPresent()){
            return new ResponseObject(false, "User with extension " + sentEquipment.getUserExt() + " cannot be found");
        }
        if(!adminOptional.isPresent()){
            return new ResponseObject(false, "Admin with extension " + sentEquipment.getAdminExt() + " cannot be found");
        }
        if(!equipmentOptional.isPresent()){
            return new ResponseObject(false, "Equipment with barcode " + sentEquipment.getBarcode() + " cannot be found");
        }
        Users user = usersOptional.get();
        Admin admin = adminOptional.get();
        Equipment equipment = equipmentOptional.get();
        if(!equipment.isInStock()){
            return new ResponseObject(false, "Equipment is currently checked out. Please check equipment in before checking out again.");
        }

        EquipmentCheckoutHistory history = new EquipmentCheckoutHistory(equipment, user, currentTime, admin);

        equipmentCheckoutDAO.save(history);

        equipment.setInStock(false);
        equipmentDAO.save(equipment);

        return new ResponseObject(true, null);
    }
    public ResponseObject checkin(String barcode){
        //Creates a new date object to get exact time of creation of user
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        Optional<Equipment> equipmentOptional = equipmentDAO.findById(barcode);
        if(!equipmentOptional.isPresent()){
            return new ResponseObject(false, "Equipment with barcode " + barcode + " cannot be found");
        }
        Equipment equipmentToFind = equipmentOptional.get();
        List<EquipmentCheckoutHistory> historyList = equipmentCheckoutDAO.findByEquipment(equipmentToFind);
        if(historyList == null){
            return new ResponseObject(false, "Equipment with barcode " + barcode + " has not ever been checked out");
        }
        if(equipmentToFind.isInStock()){
            return new ResponseObject(false, "Equipment with barcode " + barcode + " is not currently checked out");
        }
        equipmentToFind.setInStock(true);
        equipmentDAO.save(equipmentToFind);

        Comparator<EquipmentCheckoutHistory> cmp = Comparator.comparing(EquipmentCheckoutHistory::getCheckoutDate);
        EquipmentCheckoutHistory recent = Collections.max(historyList, cmp);

        recent.setCheckinDate(currentTime);
        equipmentCheckoutDAO.save(recent);
        return new ResponseObject(true, null);
    }
}
