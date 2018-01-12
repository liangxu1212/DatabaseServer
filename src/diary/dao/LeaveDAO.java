package diary.dao;

import diary.bean.Leaves;
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
    public void attachDirty(Leaves leave){
        sessionFactory.getCurrentSession().saveOrUpdate(leave);
    }
    public Leaves findLeaveById(String id){
        String hql="from Leave where leaveId="+id;
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return (Leaves) q.uniqueResult();
    }
    public List<Leaves> search(String id, String from, String to){
        String hql="from Leaves where 1=1";
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
    public int max(){
        String hql="select max(leaveId) from Leaves";
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return (int) q.uniqueResult();
    }
}
