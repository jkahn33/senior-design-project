package senior.design.group10.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.equipment.PrinterReservations;

@Repository
public interface PrinterReservationDAO extends JpaRepository<PrinterReservations, Integer>
{
	
	//Returns the dates between two entered dates
	@Query(value= "Select * from printer_res where jobSchedule between :startTime and :endTime or jobScheduleEnd between :startTime and :endTime", nativeQuery = true)
	List <PrinterReservations> checkTimeAvailable(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
	
	//Check the the closest job schedule start and check the end time
	//if the time sent is between the two then send false
}
