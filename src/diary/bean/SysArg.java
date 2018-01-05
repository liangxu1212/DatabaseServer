package diary.bean;

import javax.persistence.*;

/**
 * Created by MSI on 2018/1/5.
 */
@Entity
@Table(name = "sys_arg")
public class SysArg {
    private int argId;
    private String argContent;
    private String argCategory;

    @Id
    @Column(name = "arg_id", nullable = false)
    public int getArgId() {
        return argId;
    }

    public void setArgId(int argId) {
        this.argId = argId;
    }

    @Basic
    @Column(name = "arg_content", nullable = true, length = 100)
    public String getArgContent() {
        return argContent;
    }

    public void setArgContent(String argContent) {
        this.argContent = argContent;
    }

    @Basic
    @Column(name = "arg_category", nullable = true, length = 45)
    public String getArgCategory() {
        return argCategory;
    }

    public void setArgCategory(String argCategory) {
        this.argCategory = argCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysArg sysArg = (SysArg) o;

        if (argId != sysArg.argId) return false;
        if (argContent != null ? !argContent.equals(sysArg.argContent) : sysArg.argContent != null) return false;
        if (argCategory != null ? !argCategory.equals(sysArg.argCategory) : sysArg.argCategory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = argId;
        result = 31 * result + (argContent != null ? argContent.hashCode() : 0);
        result = 31 * result + (argCategory != null ? argCategory.hashCode() : 0);
        return result;
    }
}
