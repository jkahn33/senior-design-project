package senior.design.group10.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import senior.design.group10.objects.user.UserLoginHistory;

@Repository
public interface UserLoginHistoryDAO extends CrudRepository<UserLoginHistory, String> {
	
}
