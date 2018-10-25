<<<<<<< HEAD
package main.java.equipmentData;

import java.sql.Timestamp;


public class EquipmentCheckoutHistory
{
	private String barcode;
	private String username;
	private String goingToLocation;
	private Timestamp CheckoutDate;
	private Timestamp CheckingDate;
	private String AdminID;
=======
package equipmentData;

import java.sql.Timestamp;

import senior.design.group10.objects.Column;

@Entity
@Table(name = "equipment_checkout")

public class EquipmentCheckoutHistory
{
	@Id
	
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
	
	public EquipmentCheckoutHistory(String barcode, String username, Timestamp checkoutDate,Timestamp checkingDate, String adminID)
	{
		this.barcode = barcode;
		this.username = username;
		this.checkingDate = checkingDate;
		this.checkoutDate = checkoutDate;
		this.adminID = adminID;
	}
>>>>>>> master
	
}
