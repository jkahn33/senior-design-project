package senior.design.group10.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senior.design.group10.objects.TestWrap;

import java.util.List;

@Component
public class HibernateTest {
    @Autowired
    private SessionFactory sessionFactory;

    public List getUserDetails() {
        Criteria criteria = sessionFactory.openSession().createCriteria(TestWrap.class);
        return criteria.list();
    }
}
