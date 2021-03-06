package senior.design.group10.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.user.Admin;

/**
 * DAO to handle database access for admin table
 */
@Repository
public interface AdminDAO extends CrudRepository<Admin, String> {
}
