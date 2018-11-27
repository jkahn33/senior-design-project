package senior.design.group10.dao;

import org.springframework.data.repository.CrudRepository;
import senior.design.group10.objects.equipment.EquipmentCheckoutHistory;

public interface EquipmentCheckoutDAO extends CrudRepository<EquipmentCheckoutHistory, Integer> {
}
