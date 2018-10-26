package senior.design.group10.objects.equipment;

import org.hibernate.annotations.GenericGenerator;

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
	@Column
	private String barcode;
	@Column
	private String username;
	@Column
	private Timestamp checkoutDate;
	@Column
	private Timestamp checkingDate;
	@Column
	private String adminID;

	public EquipmentCheckoutHistory(){

	}
	
	public EquipmentCheckoutHistory(String barcode, String username, Timestamp checkoutDate,Timestamp checkingDate, String adminID)
	{
		this.barcode = barcode;
		this.username = username;
		this.checkingDate = checkingDate;
		this.checkoutDate = checkoutDate;
		this.adminID = adminID;
	}
	
}
