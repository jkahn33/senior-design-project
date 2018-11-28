package senior.design.group10.objects.equipment;

import org.hibernate.annotations.GenericGenerator;
import senior.design.group10.objects.user.Admin;
import senior.design.group10.objects.user.Users;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "equipment_checkout")
public class EquipmentCheckoutHistory
{
    @Id
    @Column(name="checkout_id")
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    private int id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="barcode")
    private Equipment equipment;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_ext")
    private Users user;
    @Column
    private Timestamp checkoutDate;
    @Column
    private Timestamp checkinDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="admin_ext")
    private Admin admin;

    public EquipmentCheckoutHistory(){

    }

    public EquipmentCheckoutHistory(Equipment equipment, Users user, Timestamp checkoutDate, Admin admin) {
        this.equipment = equipment;
        this.user = user;
        this.checkoutDate = checkoutDate;
        this.admin = admin;
    }

//    public EquipmentCheckoutPK getId() {
//        return new EquipmentCheckoutPK(equipment.getBarcode(), checkoutDate);
//    }
//
//    public void setId(EquipmentCheckoutPK id) {
//        this.equipment = id.getEquipment();
//        this.checkoutDate = id.getCheckoutDate();
//    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Users getUser() {
        return user;
    }

    public Timestamp getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Timestamp checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Timestamp getCheckoutDate() {
        return checkoutDate;
    }
}