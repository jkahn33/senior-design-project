package senior.design.group10.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.user.Users;

/**
 * DAO to handle database access for breakout_res table
 */
public interface BreakoutReservationDAO extends JpaRepository <BreakoutReservations, Integer>
{
	//Modify the query so that it takes the printer res into consideration

		public List<BreakoutReservations> findAllByUser(Users users);

		//Returns the dates between two entered dates
		@Query(value= "Select * from breakout_res where (resSchedule between :startTime and :endTime or resScheduleEnd between :startTime and :endTime) and reservable_type = 'Breakout' and reservable_id = :res_id", nativeQuery = true)
		List <BreakoutReservations> checkTimeAvailable(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("res_id") String res_id);

		//Check the the closest job schedule start and check the end time
		//if the time sent is between the two then send false
		@Query(value = "select * from breakout_res where breakout_res.resSchedule < :startTime and reservable_type = 'Breakout' and reservable_id = :res_id  order by breakout_res.resSchedule desc limit 1", nativeQuery = true)
		Optional<BreakoutReservations> checkNestedReservation(@Param("startTime") Date startTime, @Param("res_id") String res_id);
		//Select * from printer_res where (jobSchedule between '2007-09-22 10:19:10' and '2007-09-23 09:19:10'  or jobScheduleEnd between '2007-09-22 10:19:10' and '2007-09-23 09:19:10' ) and reservable_type = 'Printer' and reservable_id = 'B'
		
		//Return the events that have started and have an end date greater than or = today
		@Query(value = "Select * from breakout_res where resScheduleEnd >= :currentTime and resSchedule <= :tomorrow", nativeQuery = true)
		List <BreakoutReservations> getCurrentBreakoutEvents(@Param("currentTime") String currentTime, @Param("tomorrow") String tomorrow);
}
