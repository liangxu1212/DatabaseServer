package diary.bean;

import javax.persistence.*;

/**
 * Created by MSI on 2017/12/27.
 */
@Entity
@Table(name="clerks")
public class Clerks {
    private int clerkId;
    private String name;
    private String password;
    private Integer identity;
    private Integer departmentId;

    @Id
    @Column(name = "clerk_id", nullable = false)
    public int getClerkId() {
        return clerkId;
    }

    public void setClerkId(int clerkId) {
        this.clerkId = clerkId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "identity", nullable = true)
    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    @Basic
    @Column(name = "department_id", nullable = true)
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clerks clerks = (Clerks) o;

        if (clerkId != clerks.clerkId) return false;
        if (name != null ? !name.equals(clerks.name) : clerks.name != null) return false;
        if (password != null ? !password.equals(clerks.password) : clerks.password != null) return false;
        if (identity != null ? !identity.equals(clerks.identity) : clerks.identity != null) return false;
        if (departmentId != null ? !departmentId.equals(clerks.departmentId) : clerks.departmentId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clerkId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (identity != null ? identity.hashCode() : 0);
        result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
        return result;
    }
}
