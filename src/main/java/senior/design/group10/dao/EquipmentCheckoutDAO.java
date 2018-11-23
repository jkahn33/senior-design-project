package senior.design.group10.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.EquipmentCheckoutHistory;

import java.util.List;

public interface EquipmentCheckoutDAO extends JpaRepository<EquipmentCheckoutHistory, Integer> {
    List<EquipmentCheckoutHistory> findByEquipment(Equipment equipment);
}
