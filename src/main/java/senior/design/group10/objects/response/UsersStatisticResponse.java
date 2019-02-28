package senior.design.group10.objects.response;

import java.sql.Timestamp;

/**
 * Interface used to cast results from the SQL join operation when getting user login queries
 */
public interface UsersStatisticResponse {
    String getName();
    String getFiveDigExt();
    Timestamp getLoginDateTime();
}
