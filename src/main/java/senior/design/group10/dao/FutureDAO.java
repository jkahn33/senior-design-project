package senior.design.group10.dao;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import senior.design.group10.objects.tv.Future;

@Repository
public interface FutureDAO extends CrudRepository <Future, Integer>

{
	@Query(value= "Select * from future where futureEndDate >= :currentDate", nativeQuery = true)
	List <Future> getcurrentFuture(@Param("currentDate") Date currentDate);
	
	@Modifying
    @Transactional
	@Query(value= "Delete from future where futureEndDate <= :currentDate", nativeQuery = true)
	void deletPastFuture(@Param("currentDate") String currentDate);
}
