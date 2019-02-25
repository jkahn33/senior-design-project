package senior.design.group10.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.tv.Pi;

@Repository
public interface PiDAO extends CrudRepository<Pi, Integer>
{
	//Query ip to assure its not reused by any other pis
	@Query(value = "Select * from pi where ip = :ip limit 1", nativeQuery = true)
	Optional<Pi> checkIP(@Param("ip") String ip);
}
