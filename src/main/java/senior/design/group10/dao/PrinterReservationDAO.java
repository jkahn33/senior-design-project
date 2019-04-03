package senior.design.group10.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.equipment.PrinterUsageHours;
import senior.design.group10.objects.equipment.PrinterUsageUsers;

/**
 * DAO to handle database access for printer_res table
 */
@Repository
public interface PrinterReservationDAO extends JpaRepository<PrinterReservations, Integer>
{
	//Modify the query so that it takes the printer res into consideration
	
	//Returns the dates between two entered dates
	@Query(value= "Select * from printer_res where (jobSchedule between :startTime and :endTime or jobScheduleEnd between :startTime and :endTime) and reservable_type = 'Printer' and reservable_id = :res_id", nativeQuery = true)
	List <PrinterReservations> checkTimeAvailable(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("res_id") String res_id);
	
	//Check the the closest job schedule start and check the end time
	//if the time sent is between the two then send false
	@Query(value = "select * from printer_res where printer_res.jobschedule < :startTime and reservable_type = 'Printer' and reservable_id = :res_id  order by printer_res.jobschedule desc limit 1", nativeQuery = true)
	Optional<PrinterReservations> checkNestedReservation(@Param("startTime") Date startTime, @Param("res_id") String res_id);
	//Select * from printer_res where (jobSchedule between '2007-09-22 10:19:10' and '2007-09-23 09:19:10'  or jobScheduleEnd between '2007-09-22 10:19:10' and '2007-09-23 09:19:10' ) and reservable_type = 'Printer' and reservable_id = 'B'

	@Query(value = "select reservable_id as printerId, SUM(TIME_TO_SEC(TIMEDIFF(jobScheduleEnd, jobSchedule))) as totalHours " +
			"from printer_res where reservable_id = :printer_id", nativeQuery = true)
	PrinterUsageHours getUsageHoursById(@Param("printer_id") String printerId);

	@Query(value = "select users.name, users.fiveDigExt, users.depCode as department, count(printer_res.user_ext) as occurrences from printer_res " +
			"INNER JOIN users ON printer_res.user_ext = users.fiveDigExt " +
			"GROUP BY user_ext;", nativeQuery = true)
	List<PrinterUsageUsers> getUserUsage();
}
