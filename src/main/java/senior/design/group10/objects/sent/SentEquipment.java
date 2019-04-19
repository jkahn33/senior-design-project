package senior.design.group10.objects.sent;

public class SentEquipment {
    private String barcode;
    private String userID;
    private String adminID;

    public SentEquipment(){}

    public SentEquipment(String barcode, String userID, String adminID) {
        this.barcode = barcode;
        this.userID = userID;
        this.adminID = adminID;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getUserID() {
        return userID;
    }

    public String getAdminID() {
        return adminID;
    }
}
