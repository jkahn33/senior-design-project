package senior.design.group10.objects.sent;

public class NewAdmin {
    private String name;
    private String ext;
    private String password;

    public NewAdmin(){}

    public NewAdmin(String name, String ext, String password) {
        this.name = name;
        this.ext = ext;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getExt() {
        return ext;
    }

    public String getPassword() {
        return password;
    }
}
