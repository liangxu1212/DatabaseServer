package diary.dao;

import diary.bean.SysArg;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by MSI on 2017/12/27.
 */
public class SysArgDAO {
    private SessionFactory sessionFactory;
    @Resource
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    public void attachDirty(SysArg sysArg){
        sessionFactory.getCurrentSession().saveOrUpdate(sysArg);
    }
    public SysArg findByCategory(String category){
        String hql="from SysArg where argCategory='"+category+"'";
        Query q=this.sessionFactory.getCurrentSession().createQuery(hql);
        return (SysArg) q.uniqueResult();
    }
    public void modifyArg(String category,String value){
        String hql="update SysArg set argContent='"+value+"' where argCategory='"+category+"'";
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        q.executeUpdate();
    }
    public void removeDay(String day){
        String hql="delete from SysArg where argContent='"+day+"'";
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        q.executeUpdate();
    }
    public List<SysArg> listVacation(){
        String hql="from SysArg where argCategory='vacation'";
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return  q.list();
    }
}
