package diary.dao;

import diary.bean.Trip;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by MSI on 2017/12/27.
 */
public class TripDAO {
    private SessionFactory sessionFactory;
    @Resource
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    public void attachDirty(Trip trip){
        sessionFactory.getCurrentSession().saveOrUpdate(trip);
    }
    public List<Trip> search(String clerk,String department,String from,String to){
        String hql="";
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return q.list();
    }

}
