package senior.design.group10.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import senior.design.group10.objects.tv.Calendar;

@Repository
public interface CalendarDAO extends JpaRepository <Calendar, Integer> {
	//Returns the dates between two entered dates
	@Query(value= "select * from calendar where (eventDate between :startTime and :endTime)", nativeQuery = true)
	List <Calendar> getEventsByTime(@Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime);
}
