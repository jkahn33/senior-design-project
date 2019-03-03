package senior.design.group10.objects.sent;

public class SentUser 
{
    private String name;
    private String ext;
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
