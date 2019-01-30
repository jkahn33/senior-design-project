package senior.design.group10.objects.sent;

public class EditAdmin {
    private String oldExt;
    private String ext;
    private String name;
    private String newPass;

    public EditAdmin(){}

    public EditAdmin(String oldExt, String ext, String name, String newPass) {
        this.oldExt = oldExt;
        this.ext = ext;
        this.name = name;
        this.newPass = newPass;
    }

    public String getOldExt(){
        return oldExt;
    }

    public String getExt() {
        return ext;
    }

    public String getName() {
        return name;
    }

    public String getNewPass() {
        return newPass;
    }
}
