package vip.efactory.modules.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "sys_dept")
public class Dept extends BaseEntity implements Serializable {

    /**
     * ID
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @NotNull
    private Boolean enabled;

    /**
     * 上级部门
     */
    @Column(name = "pid", nullable = false)
    @NotNull
    private Long pid;

    @JsonIgnore
    @ManyToMany(mappedBy = "depts")
    private Set<Role> roles;

//    @Column(name = "create_time")
//    @CreationTimestamp
//    private Timestamp createTime;

    public @interface Update {
    }
}
