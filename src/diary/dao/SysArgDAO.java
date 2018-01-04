package diary.dao;

import diary.bean.SysArg;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

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
}
