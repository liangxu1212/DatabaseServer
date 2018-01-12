package diary.dao;

import diary.bean.History;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;

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

    public List<History> listHistory(String from,String to){
        String hql="from History where 1=1";
        if(from!=null){
            hql+=" and historyTime>='"+from+"'";
        }
        if(to!=null){
            hql+=" and historyTime<='"+to+"'";
        }
        System.out.println(hql);
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return q.list();
    }
}
