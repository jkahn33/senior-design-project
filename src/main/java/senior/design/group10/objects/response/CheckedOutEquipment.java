package senior.design.group10.objects.response;

import java.sql.Timestamp;

public interface CheckedOutEquipment {
    String getEquipmentName();
    String getBarcode();
    String getAdminName();
    String getUserName();
    Timestamp getCheckoutDate();
}
