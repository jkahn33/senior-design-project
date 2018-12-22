package senior.design.group10.objects.response;

import java.sql.Timestamp;

public interface EquipmentUsageResponse {
    String getEquipmentName();
    String getBarcode();
    int getAmtUsed();
    Timestamp getLastCheckedOut();
}
