package senior.design.group10.objects.response;

public class NewUserResponse {
    public boolean success;
    public String message;

    public NewUserResponse(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
