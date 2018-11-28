package senior.design.group10.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import senior.design.group10.objects.equipment.ReservableKey;
import senior.design.group10.objects.equipment.Reservables;


@Repository
public interface ReservablesDAO extends CrudRepository<Reservables, ReservableKey>
{

}
