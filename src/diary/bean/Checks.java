package diary.bean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by MSI on 2018/1/5.
 */
@Entity
@Table(name="checks")
public class Checks {
    private int checkId;
    private Integer clerkId;
    private Date checkTime;
    private Integer category;
    private Integer state;

    @Id
    @Column(name = "check_id", nullable = false)
    public int getCheckId() {
        return checkId;
    }

    public void setCheckId(int checkId) {
        this.checkId = checkId;
    }

    @Basic
    @Column(name = "clerk_id", nullable = true)
    public Integer getClerkId() {
        return clerkId;
    }

    public void setClerkId(Integer clerkId) {
        this.clerkId = clerkId;
    }

    @Basic
    @Column(name = "check_time", nullable = true)
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    @Basic
    @Column(name = "category", nullable = true)
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Basic
    @Column(name = "state", nullable = true)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Checks checks = (Checks) o;

        if (checkId != checks.checkId) return false;
        if (clerkId != null ? !clerkId.equals(checks.clerkId) : checks.clerkId != null) return false;
        if (checkTime != null ? !checkTime.equals(checks.checkTime) : checks.checkTime != null) return false;
        if (category != null ? !category.equals(checks.category) : checks.category != null) return false;
        if (state != null ? !state.equals(checks.state) : checks.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = checkId;
        result = 31 * result + (clerkId != null ? clerkId.hashCode() : 0);
        result = 31 * result + (checkTime != null ? checkTime.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
