package senior.design.group10.objects.sent;

import javax.xml.bind.annotation.XmlElement;
import java.sql.Date;
import java.sql.Timestamp;

public class SentLoginHistory {

    private String ext;

    public SentLoginHistory(){}

    public SentLoginHistory(String ext){
        this.ext = ext;
    }
    
    public String getExt() {
    	return ext;
    }
}
