package senior.design.group10.objects.response;

public class ResponseObject {
    public boolean success;
    public String message;

    public ResponseObject(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
