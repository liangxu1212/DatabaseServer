package diary.dao;

import diary.bean.Leave;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by MSI on 2017/12/27.
 */
public class LeaveDAO {
    private SessionFactory sessionFactory;
    @Resource
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    public void attachDirty(Leave leave){
        sessionFactory.getCurrentSession().saveOrUpdate(leave);
    }
    public Leave findLeaveById(String id){
        String hql="from Leave where leaveId="+id;
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return (Leave) q.uniqueResult();
    }
    public List<Leave> search(String id, String from, String to){
        String hql="from Leave where 1=1";
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
