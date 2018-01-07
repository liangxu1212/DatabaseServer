package diary.dao;

import diary.bean.Department;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by MSI on 2017/12/27.
 */
public class DepartmentDAO {
    private SessionFactory sessionFactory;
    @Resource
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    public List<Department> listDepartment(){
        String hql="from Department where 1=1";
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return q.list();
    }
    public Department findDepartmentById(String id){
        String hql="from Department where departmentId="+id;
        Query q=sessionFactory.getCurrentSession().createQuery(hql);
        return (Department) q.uniqueResult();
    }
    public void attachDirty(Department department){sessionFactory.getCurrentSession().saveOrUpdate(department);}
}
