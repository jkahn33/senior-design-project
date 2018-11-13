package senior.design.group10.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.hibernate.mapping.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.equipment.PrinterReservations;

@Repository
public interface PrinterReservationDAO extends JpaRepository<PrinterReservations, Integer>
{
	List<PrinterReservations> findAllJobScheduleBetween
	(Timestamp jobSchedule, Timestamp jobScheduleEnd);
}
