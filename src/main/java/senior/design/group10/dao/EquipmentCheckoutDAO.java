package senior.design.group10.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import senior.design.group10.objects.equipment.EquipmentCheckoutHistory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class EquipmentCheckoutDAO {
    private final SessionFactory sessionFactory;
    private final Session session;

    @Autowired
    public EquipmentCheckoutDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        
        sessionFactory.openSession();
        session = sessionFactory.getCurrentSession();
    }

    public void save(EquipmentCheckoutHistory history){
        session.save(history);
    }
    public List<EquipmentCheckoutHistory> findByBarcode(String barcode){
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<EquipmentCheckoutHistory> query = builder.createQuery(EquipmentCheckoutHistory.class);

        Root<EquipmentCheckoutHistory> root = query.from(EquipmentCheckoutHistory.class);

        Predicate hasBarcode = builder.equal(root.get("barcode"), barcode);

        query.where(hasBarcode);
        return em.createQuery(query.select(root)).getResultList();
    }
}
