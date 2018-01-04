package diary.dao;

import diary.bean.Leave;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

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
}
