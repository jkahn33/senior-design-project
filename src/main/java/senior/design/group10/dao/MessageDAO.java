package senior.design.group10.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import senior.design.group10.objects.equipment.BreakoutReservations;
import senior.design.group10.objects.tv.Messages;

@Repository
public interface MessageDAO extends CrudRepository<Messages, Integer>
{
	@Query(value= "Select * from messages where (resSchedule between :startTime and :endTime or resScheduleEnd between :startTime and :endTime) and reservable_type = 'Breakout' and reservable_id = :res_id", nativeQuery = true)
	List <BreakoutReservations> checkTimeAvailable(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("res_id") String res_id);
	
}
