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
    public Trip findTripById(String id){
        String hql="from Trip where tripId="+id;
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return (Trip) q.uniqueResult();
    }
    public List<Trip> search(String id,String from,String to){
        String hql="from Trip where 1=1";
        if(id!=null){
            hql+=" and clerkId="+id;
        }
        if(from!=null){
            hql+=" and applyTime>='"+from+"'";
        }
        if(to!=null){
            hql+=" and applyTime<='"+to+"'";
        }
        System.out.println(hql);
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return q.list();
    }

}
