package senior.design.group10.objects.response;

public class ReturnAdmin {
    private String name;
    private String fiveDigExt;

    public ReturnAdmin(){}

    public ReturnAdmin( String name, String fiveDigExt)
    {
        this.name = name;
        this.fiveDigExt = fiveDigExt;
    }

    public String getName() {
        return name;
    }

    public String getFiveDigExt() {
        return fiveDigExt;
    }
}
