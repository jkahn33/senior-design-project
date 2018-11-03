package senior.design.group10.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.equipment.Equipment;

@Repository
public interface EquipmentDAO extends CrudRepository<Equipment, String> {
}
