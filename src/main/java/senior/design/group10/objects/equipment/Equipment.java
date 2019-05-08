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
	private String manufacturerName;
	@Column
	private String modelNumber;
	@Column
	private String serialNumber;
	@Column
	private String plantNumber;
	@Column
	private boolean inStock;
	@Column
	private Timestamp added;

	public Equipment(){}

	public Equipment(String barcode, String equipmentName, String manufacturerName, String modelNumber, String serialNumber, String plantNumber, boolean inStock, Timestamp added) {
		this.barcode = barcode;
		this.equipmentName = equipmentName;
		this.manufacturerName = manufacturerName;
		this.modelNumber = modelNumber;
		this.serialNumber = serialNumber;
		this.plantNumber = plantNumber;
		this.inStock = inStock;
		this.added = added;
	}

	public String getBarcode() {
		return barcode;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getPlantNumber() {
		return plantNumber;
	}

	public boolean isInStock() {
		return inStock;
	}

	public Timestamp getAdded() {
		return added;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}
}
