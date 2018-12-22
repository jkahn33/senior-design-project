package senior.design.group10.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.response.UsersStatisticResponse;
import senior.design.group10.objects.user.Users;

import java.util.Date;
import java.util.List;

@Repository
public interface UsersDAO extends CrudRepository<Users, String> {

    @Query(value= "select users.name AS name, users.fiveDigExt AS fiveDigExt, user_login_hist.loginDateTime AS loginDateTime" +
            " from user_login_hist inner join users on user_login_hist.user_ext=users.fiveDigExt " +
            "where(loginDateTime between :startTime and :endTime)", nativeQuery = true)
    List <UsersStatisticResponse> getUsersBetweenDates(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
