package diary.bean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by MSI on 2017/12/27.
 */
@Entity
@Table(name="history")
public class History {
    private int historyId;
    private Integer clerkId;
    private Integer category;
    private Date historyTime;
    private String description;

    @Id
    @Column(name = "history_id", nullable = false)
    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
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
    @Column(name = "category", nullable = true)
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @Basic
    @Column(name = "history_time", nullable = true)
    public Date getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(Date historyTime) {
        this.historyTime = historyTime;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 45)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        History history = (History) o;

        if (historyId != history.historyId) return false;
        if (clerkId != null ? !clerkId.equals(history.clerkId) : history.clerkId != null) return false;
        if (category != null ? !category.equals(history.category) : history.category != null) return false;
        if (historyTime != null ? !historyTime.equals(history.historyTime) : history.historyTime != null) return false;
        if (description != null ? !description.equals(history.description) : history.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = historyId;
        result = 31 * result + (clerkId != null ? clerkId.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (historyTime != null ? historyTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
