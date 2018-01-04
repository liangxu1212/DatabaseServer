package diary.dao;

import diary.bean.Clerks;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by MSI on 2017/12/27.
 */

public class ClerksDAO {
    private SessionFactory sessionFactory;
    @Resource
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    public Clerks findById(String id){
        String hql="from Clerks where clerkId="+id;
        System.out.println(hql);
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return (Clerks) q.uniqueResult();
    }
    public List<Clerks> listClerks(String id,String name,String department_id){
        String hql="from Clerks where 1=1";
        if(id!=null){
            hql+=" and clerkId="+id;
        }
        if(name!=null){
            hql+=" and name like '%"+name+"%'";
        }
        if(department_id!=null){
            hql+=" and departmentId="+department_id;
        }
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return q.list();
    }
}
