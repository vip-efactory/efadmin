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
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "sys_menu")
public class Menu extends BaseEntity implements Serializable {

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {Update.class})
    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true)
    @NotNull
    private Long sort;

    @Column(name = "path")
    private String path;

    private String component;

    private String icon;

    /**
     * 上级菜单ID
     */
    @Column(name = "pid", nullable = false)
    private Long pid;

    /**
     * 是否为外链 true/false
     */
    @Column(name = "i_frame")
    private Boolean iFrame;

    @ManyToMany(mappedBy = "menus")
    @JsonIgnore
    private Set<Role> roles;

//    @CreationTimestamp
//    @Column(name = "create_time")
//    private Timestamp createTime;

    public interface Update {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
