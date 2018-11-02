package senior.design.group10.objects.sent;

public class AdminInQuestion {
    private String ext;
    private String password;

    public AdminInQuestion(){}

    public AdminInQuestion(String ext, String password){
        this.ext = ext;
        this.password = password;
    }

    public String getExt() {
        return ext;
    }

    public String getPassword() {
        return password;
    }
}
