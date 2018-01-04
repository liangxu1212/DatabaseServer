package diary.dao;

import diary.bean.History;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

/**
 * Created by MSI on 2017/12/27.
 */
public class HistoryDAO {
    private SessionFactory sessionFactory;
    @Resource
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    public void attachDirty(History history){
        sessionFactory.getCurrentSession().saveOrUpdate(history);
    }
}
