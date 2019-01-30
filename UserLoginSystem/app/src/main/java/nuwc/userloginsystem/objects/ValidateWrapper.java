package nuwc.userloginsystem.objects;

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
