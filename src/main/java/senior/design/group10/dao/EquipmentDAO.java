package senior.design.group10.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.equipment.Equipment;

/**
 * DAO to handle database access for equipment table
 */
@Repository
public interface EquipmentDAO extends CrudRepository<Equipment, String> {
}
