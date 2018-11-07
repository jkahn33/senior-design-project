package senior.design.group10.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senior.design.group10.dao.AdminDAO;
import senior.design.group10.dao.EquipmentCheckoutDAO;
import senior.design.group10.dao.EquipmentDAO;
import senior.design.group10.dao.PrinterReservationDAO;
import senior.design.group10.dao.UsersDAO;
import senior.design.group10.objects.equipment.PrinterReservations;
import senior.design.group10.objects.response.ResponseObject;
import senior.design.group10.objects.sent.SentPrinterReservation;
import senior.design.group10.objects.user.Users;

@Service
public class PrinterService 
{
	private final PrinterReservationDAO printerDAO;
	private final UsersDAO usersDAO;
	@Autowired
	public PrinterService( UsersDAO usersDAO, PrinterReservationDAO printerDAO)
	{
		this.usersDAO = usersDAO;
		this.printerDAO = printerDAO;
	}
	
	public ResponseObject addPrintRes(SentPrinterReservation printer)
	{
        Optional<Users> usersOptional = usersDAO.findById(printer.getUserExt());
        if(!usersOptional.isPresent()){
            return new ResponseObject(false, "User with extension " + printer.getUserExt() + " cannot be found");
        }

		return new ResponseObject(true,null);
	}

}
