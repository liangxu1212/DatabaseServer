package diary.dao;

import diary.bean.Checks;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by MSI on 2017/12/27.
 */
public class CheckDAO {
    private SessionFactory sessionFactory;
    @Resource
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    public void attachDirty(Checks check){
        this.sessionFactory.getCurrentSession().saveOrUpdate(check);
    }
    public List<Checks> listCheck(String id, String department_id,String category, String from, String to){
        String hql="from Checks where 1=1";
        if(id!=null){
            hql+=" and clerkId="+id;
        }
        if(category!=null){
            hql+=" and category="+category;
        }
        if(from!=null){
            hql+=" and checkTime>='"+from+"'";
        }
        if(to!=null){
            hql+=" and checkTime<='"+to+"'";
        }
        System.out.println(hql);
        Query q=this.sessionFactory.getCurrentSession().createQuery(hql);
        return q.list();
    }
    public int max(){
        String hql="select max(checkId) from Checks";
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return (int) q.uniqueResult();
    }
}
