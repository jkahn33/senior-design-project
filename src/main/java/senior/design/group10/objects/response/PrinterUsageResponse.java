package senior.design.group10.objects.response;

import senior.design.group10.objects.equipment.PrinterUsageUsers;

import java.util.List;

public class PrinterUsageResponse {
    private final String id;
    private final String hours;

    public PrinterUsageResponse(String id, String hours) {
        this.id = id;
        this.hours = hours;
    }

    public String getId() {
        return id;
    }

    public String getHours() {
        return hours;
    }
}
