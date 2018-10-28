package senior.design.group10.objects.sent;

import javax.xml.bind.annotation.XmlElement;

public class SentUser {
    @XmlElement
    private String name;
    @XmlElement
    private String ext;
    @XmlElement
    private String dep;

    public SentUser(){}

    public SentUser(String name, String ext, String dep){
        this.name = name;
        this.ext = ext;
        this.dep = dep;
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public String getDep() {
        return dep;
    }
}
