package nuwc.userloginsystem.objects;

import java.util.Comparator;

public class NamesComparator implements Comparator<Users> {
    @Override
    public int compare(Users u1, Users u2) {
        return u1.getName().compareTo(u2.getName());
    }
}
