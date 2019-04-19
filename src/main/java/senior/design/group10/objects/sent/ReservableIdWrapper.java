package senior.design.group10.objects.sent;

import java.util.List;
import java.util.ArrayList;

public class ReservableIdWrapper {
    private List<String> ids;
    public List<String> getIds() {
        return ids;
    }
    public ReservableIdWrapper(List<String> ids) {
        this.ids = ids;
    }
    public ReservableIdWrapper(String ids) {
    	this.ids = new ArrayList<String>();
    	String currentItem = "";
    	for(int i = 1; i < ids.length() - 1; i++) {
    		if(ids.charAt(i) == ',') {
    			this.ids.add(currentItem);
    			currentItem = "";
    		} else if(ids.charAt(i) != ' ') {
    			currentItem += ids.charAt(i);
    		}
    	}
    	this.ids.add(currentItem);
    	System.out.println("FDKSJLLSKDFJLKSDJF" + ids);
    }
}
