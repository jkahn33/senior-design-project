package senior.design.group10.objects.equipment;

import javax.persistence.*;
import java.sql.Timestamp;

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
	@Column
	private Timestamp added;

	public Equipment(){}
	
	public Equipment(String barcode, String equipmentName, boolean inStock, Timestamp added)
	{
		this.barcode = barcode;
		this.equipmentName = equipmentName;
		this.inStock = inStock;
		this.added = added;
	}

	public String getBarcode() {
		return barcode;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public boolean getInStock() {
		return inStock;
	}

	public Timestamp getAdded(){
		return added;
	}

	public void setInStock(boolean inStock){
		this.inStock = inStock;
	}
}
