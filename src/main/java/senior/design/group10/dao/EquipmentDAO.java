package senior.design.group10.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.response.CheckedOutEquipment;
import senior.design.group10.objects.response.EquipmentUsageResponse;

import java.util.List;

/**
 * DAO to handle database access for equipment table
 */
@Repository
public interface EquipmentDAO extends CrudRepository<Equipment, String> {
    @Query(value = "select equipment.equipmentName, e1.barcode, (select count(*) from equipment_checkout where barcode=e1.barcode) AS amtUsed, " +
            "equipment.added AS dateAdded, e1.checkoutDate AS lastCheckedOut from equipment_checkout e1 " +
            "inner join equipment " +
            "on equipment.barcode=e1.barcode where e1.checkoutDate=(select MAX(checkoutDate) from equipment_checkout e2 where e1.barcode = e2.barcode) " +
            "group by equipment.equipmentName, e1.barcode, e1.checkoutDate", nativeQuery = true)
    List<EquipmentUsageResponse> getEquipmentUsage();

    @Query(value = "select equipment.equipmentName, equipment_checkout.barcode, admin.name AS adminName, users.name AS userName, equipment_checkout.checkoutDate " +
            "from equipment_checkout " +
            "inner join equipment on equipment.barcode=equipment_checkout.barcode " +
            "inner join admin on admin.ext=equipment_checkout.admin_ext " +
            "inner join users on users.fiveDigExt=equipment_checkout.user_ext " +
            "where equipment.inStock=false and equipment_checkout.checkinDate is null", nativeQuery = true)
    List<CheckedOutEquipment> getCheckedOutEquipment();
}
