package senior.design.group10.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.tv.Messages;

@Repository
public interface MessageDAO extends CrudRepository<Messages, Integer>
{
	@Query(value= "Select * from messages where messageEndDate >= :currentDate", nativeQuery = true)
	List <Messages> getcurrentMessages(@Param("currentDate") Date currentDate);
	
}
