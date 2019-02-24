package nuwc.userloginsystem.objects;

import java.util.List;

public class ReservableIdWrapper {
    private List<String> ids;
    public List<String> getIds() {
        return ids;
    }
    public ReservableIdWrapper(List<String> ids) {
        this.ids = ids;
    }
}
