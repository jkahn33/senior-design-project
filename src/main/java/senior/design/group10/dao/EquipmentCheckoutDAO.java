package senior.design.group10.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.equipment.Equipment;
import senior.design.group10.objects.equipment.EquipmentCheckoutHistory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
@Repository
public class EquipmentCheckoutDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public EquipmentCheckoutDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(EquipmentCheckoutHistory history){
        sessionFactory.getCurrentSession().save(history);
    }
    @Transactional
    public void update(EquipmentCheckoutHistory history){
        sessionFactory.getCurrentSession().update(history);
    }
    @Transactional
    public List<EquipmentCheckoutHistory> findByEquipment(Equipment equipmentToCheckin){
        Session session = sessionFactory.getCurrentSession();
        EntityManager em = sessionFactory.createEntityManager();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<EquipmentCheckoutHistory> query = builder.createQuery(EquipmentCheckoutHistory.class);

        Root<EquipmentCheckoutHistory> root = query.from(EquipmentCheckoutHistory.class);

        Predicate hasBarcode = builder.equal(root.get("equipment"), equipmentToCheckin);

        query.where(hasBarcode);
        return em.createQuery(query.select(root)).getResultList();
    }
}
