package senior.design.group10.objects.equipment;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class EquipmentCheckoutPK implements Serializable {
    private String equipment;
    private Timestamp checkoutDate;

    public EquipmentCheckoutPK(){}
    public EquipmentCheckoutPK(String equipment){
        this.equipment = equipment;
    }
    public EquipmentCheckoutPK(String equipment, Timestamp checkoutDate){
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

    public String getEquipment() {
        return equipment;
    }

    public Timestamp getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Timestamp checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
