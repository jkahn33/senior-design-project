package main.java.equipmentData;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name ="equipment")
public class Equipment
{
	@Id
	
	@Column
	private String barcode;
	@Column
	private String equipmentName;
	@Column
	private boolean inStock;
	
	public Equipment(String barcode, String equipmentName, boolean inStock)
	{
		this.barcode = barcode;
		this.equipmentName = equipmentName;
		this.inStock = inStock;
	}
}
