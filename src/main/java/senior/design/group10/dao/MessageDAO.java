package senior.design.group10.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.tv.Messages;

@Repository
public interface MessageDAO extends CrudRepository<Messages, Integer>
{
}
