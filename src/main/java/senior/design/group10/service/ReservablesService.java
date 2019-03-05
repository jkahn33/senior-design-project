package senior.design.group10.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.ReservablesDAO;
import senior.design.group10.objects.equipment.ReservableKey;
import senior.design.group10.objects.equipment.Reservables;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentReservable;

/**
 * Service class to handle business logic for Reservables (3D printers and breakout rooms)
 */
@Service
public class ReservablesService 
{
	private final ReservablesDAO reservablesDAO;

	@Autowired
	public ReservablesService( ReservablesDAO reservablesDAO)
	{
		this.reservablesDAO = reservablesDAO;
	}

	public ResponseObject saveNewReservable( SentReservable sentReservable)
	{
		//Optional <Reservables> reservableOptional = reservablesDAO.findById(sentReservable.getRerservableKey());
		if(reservablesDAO.existsById(sentReservable.getRerservableKey())){
			return new ResponseObject(false, "A reservable with parameters " + sentReservable.getRerservableKey().getType() + sentReservable.getRerservableKey().getID() +" already exists.");
		}
		
		Reservables reservableToSave = new Reservables(sentReservable.getRerservableKey());
		reservablesDAO.save(reservableToSave);
		return new ResponseObject(true,null);
	}
	
	public ResponseObject removeReservable(SentReservable sentReservable)
	{
		if(!reservablesDAO.existsById(sentReservable.getRerservableKey())){
			return new ResponseObject(false, "A reservable with parametres " + sentReservable.getRerservableKey().getType() + " does not exists.");
		}
		
		
		reservablesDAO.deleteById(sentReservable.getRerservableKey());
		return new ResponseObject(true,null);
	}
	
	

}
