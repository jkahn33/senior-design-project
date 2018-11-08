package senior.design.group10.objects.equipment;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="equipment")
public class Equipment
{
	@Id
	@Column(length=50)
	private String barcode;
	@Column
	private String equipmentName;
	@Column
	private boolean inStock;

	public Equipment(){}
	
	public Equipment(String barcode, String equipmentName, boolean inStock)
	{
		this.barcode = barcode;
		this.equipmentName = equipmentName;
		this.inStock = inStock;
	}

	public String getBarcode() {
		return barcode;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock){
		this.inStock = inStock;
	}
}
