package senior.design.group10.objects.response;

public class ValidateWrapper {
    private boolean success;

    public ValidateWrapper(){}

    public ValidateWrapper(boolean success){
        this.success = success;
    }

    public boolean isSuccess(){
        return success;
    }
}
