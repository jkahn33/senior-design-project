package nuwc.userloginsystem.objects;

import java.util.Comparator;

public class NamesComparator implements Comparator<ActiveUser> {
    @Override
    public int compare(ActiveUser u1, ActiveUser u2) {
        return u1.getName().compareTo(u2.getName());
    }
}
