package senior.design.group10.objects.sent;

public class SentEquipment {
    private String barcode;
    private String userExt;
    private String adminExt;

    public SentEquipment(){}

    public SentEquipment(String barcode, String userExt, String adminExt) {
        this.barcode = barcode;
        this.userExt = userExt;
        this.adminExt = adminExt;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getUserExt() {
        return userExt;
    }

    public String getAdminExt() {
        return adminExt;
    }
}
