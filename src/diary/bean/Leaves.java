package diary.bean;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by MSI on 2018/1/12.
 */
@Entity
@Table(name="leaves")
public class Leaves {
    private int leaveId;
    private Integer clerkId;
    private Integer category;
    private Integer state;
    private Integer commentId;
    private String comment;
    private Date applyTime;
    private Date updateTime;
    private String content;
    private Date from;
    private Date to;

    @Id
    @Column(name = "leave_id", nullable = false)
    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
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
    @Column(name = "state", nullable = true)
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "comment_id", nullable = true)
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "comment", nullable = true, length = 200)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "apply_time", nullable = true)
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "from", nullable = true)
    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    @Basic
    @Column(name = "to", nullable = true)
    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Leaves leaves = (Leaves) o;

        if (leaveId != leaves.leaveId) return false;
        if (clerkId != null ? !clerkId.equals(leaves.clerkId) : leaves.clerkId != null) return false;
        if (category != null ? !category.equals(leaves.category) : leaves.category != null) return false;
        if (state != null ? !state.equals(leaves.state) : leaves.state != null) return false;
        if (commentId != null ? !commentId.equals(leaves.commentId) : leaves.commentId != null) return false;
        if (comment != null ? !comment.equals(leaves.comment) : leaves.comment != null) return false;
        if (applyTime != null ? !applyTime.equals(leaves.applyTime) : leaves.applyTime != null) return false;
        if (updateTime != null ? !updateTime.equals(leaves.updateTime) : leaves.updateTime != null) return false;
        if (content != null ? !content.equals(leaves.content) : leaves.content != null) return false;
        if (from != null ? !from.equals(leaves.from) : leaves.from != null) return false;
        if (to != null ? !to.equals(leaves.to) : leaves.to != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = leaveId;
        result = 31 * result + (clerkId != null ? clerkId.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (commentId != null ? commentId.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (applyTime != null ? applyTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
