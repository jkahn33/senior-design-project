package senior.design.group10.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import senior.design.group10.objects.user.UserLoginHistory;

/**
 * DAO to handle database access for user_login_hist table
 */
@Repository
public interface UserLoginHistoryDAO extends CrudRepository<UserLoginHistory, String> {
}
