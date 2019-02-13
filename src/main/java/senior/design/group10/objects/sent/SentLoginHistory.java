package senior.design.group10.objects.sent;

import javax.xml.bind.annotation.XmlElement;
import java.sql.Date;
import java.sql.Timestamp;

public class SentLoginHistory {
    @XmlElement
    private String badgeID;

    public SentLoginHistory(){}

    public SentLoginHistory(String badgeID){
        this.badgeID = badgeID;
    }
    
    public String getBadgeID() {
    	return badgeID;
    }
}
