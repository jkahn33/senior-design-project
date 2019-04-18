package senior.design.group10.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.response.UsersStatisticResponse;
import senior.design.group10.objects.user.DebugUser;
import senior.design.group10.objects.user.SpecifcUserSearch;
import senior.design.group10.objects.user.Users;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * DAO to handle database access for users table
 */
@Repository
public interface UsersDAO extends CrudRepository<Users, String> {
    Optional<Users> findByName(String name);

    /**
     * Creates query to find all users who logged into USWRIC between two dates
     * @param startTime Date object to represent where to start the search
     * @param endTime Date object to represent where to end the search
     * @return List of users' name, badge_id, and time of login
     */
    @Query(value= "select users.name AS name, users.fiveDigExt AS badgeId, user_login_hist.loginDateTime AS loginDateTime, users.depCode" +
            " from user_login_hist inner join users on user_login_hist.user_ext=users.fiveDigExt " +
            "where(loginDateTime between :startTime and :endTime)", nativeQuery = true)
    List <UsersStatisticResponse> getUsersBetweenDates(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Query(value = "select users.name, users.depCode as dept, (select count(*) from user_login_hist where user_ext=ulh.user_ext) as occurrences, ulh.loginDateTime as lastEntered, users.creationDate from user_login_hist ulh " +
            "inner join users on users.badgeId = ulh.user_ext " +
            "where ulh.loginDateTime=(select MAX(loginDateTime) from user_login_hist ulh2 where ulh.user_ext = ulh2.user_ext) and users.badgeId= :badgeId " +
            "group by users.name, users.depCode, ulh.loginDateTime, users.creationDate, ulh.user_ext", nativeQuery=true)
    SpecifcUserSearch getSpecificUserStatsById(@Param("badgeId") String badgeId);

    @Query(value = "select users.name, users.depCode as dept, (select count(*) from user_login_hist where user_ext=ulh.user_ext) as occurrences, ulh.loginDateTime as lastEntered, users.creationDate from user_login_hist ulh " +
            "inner join users on users.badgeId = ulh.user_ext " +
            "where ulh.loginDateTime=(select MAX(loginDateTime) from user_login_hist ulh2 where ulh.user_ext = ulh2.user_ext) and users.name= :userName " +
            "group by users.name, users.depCode, ulh.loginDateTime, users.creationDate, ulh.user_ext", nativeQuery=true)
    SpecifcUserSearch getSpecificUserStatsByName(@Param("userName") String userName);

    @Query(value = "select MAX(loginDateTime) as count from user_login_hist ulh2 where user_ext = '12345'", nativeQuery = true)
    DebugUser testDebug();
}
