package senior.design.group10.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.response.UsersStatisticResponse;
import senior.design.group10.objects.user.Users;

import java.util.Date;
import java.util.List;

/**
 * DAO to handle database access for users table
 */
@Repository
public interface UsersDAO extends CrudRepository<Users, String> {

    /**
     * Creates query to find all users who logged into USWRIC between two dates
     * @param startTime Date object to represent where to start the search
     * @param endTime Date object to represent where to end the search
     * @return List of users' name, badge_id, and time of login
     */
    @Query(value= "select users.name AS name, users.fiveDigExt AS fiveDigExt, user_login_hist.loginDateTime AS loginDateTime" +
            " from user_login_hist inner join users on user_login_hist.user_ext=users.fiveDigExt " +
            "where(loginDateTime between :startTime and :endTime)", nativeQuery = true)
    List <UsersStatisticResponse> getUsersBetweenDates(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
