package vip.efactory.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Description: 权限
 *
 * @author dbdu
 * @date 19-7-10 上午11:03
 */
@Entity
@Getter
@Setter
@Table(name = "sys_permission")
public class Permission extends BaseEntity implements Serializable {

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {Update.class})
    private Long id;

    @NotBlank
    private String name;

    /**
     * 上级类目
     */
    @NotNull
    @Column(name = "pid", nullable = false)
    private Long pid;

    @NotBlank
    private String alias;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;
//
//    @CreationTimestamp
//    @Column(name = "create_time")
//    private Timestamp createTime;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", alias='" + alias + '\'' +
                '}';
    }

    public interface Update {
    }
}
