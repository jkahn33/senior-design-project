package senior.design.group10.objects.equipment;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class EquipmentCheckoutPK implements Serializable {
    private Equipment equipment;
    private Timestamp checkoutDate;

    public EquipmentCheckoutPK(Equipment equipment){
        this.equipment = equipment;
    }
    public EquipmentCheckoutPK(Equipment equipment, Timestamp checkoutDate){
        this.equipment = equipment;
        this.checkoutDate = checkoutDate;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EquipmentCheckoutPK pk = (EquipmentCheckoutPK) o;
        return equipment.equals(pk.equipment) && checkoutDate.equals(pk.checkoutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equipment, checkoutDate);
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Timestamp getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Timestamp checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
