package diary.dao;

import org.hibernate.SessionFactory;

import javax.annotation.Resource;

/**
 * Created by MSI on 2017/12/27.
 */
public class CheckDAO {
    private SessionFactory sessionFactory;
    @Resource
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }

}
