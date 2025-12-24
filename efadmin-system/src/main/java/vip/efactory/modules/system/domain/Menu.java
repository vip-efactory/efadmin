package vip.efactory.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.utils.StringUtils;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotBlank;


@Entity
@Getter
@Setter
@Table(name = "sys_menu")
public class Menu extends BaseEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)
    private Long id;

    @NotBlank
    private String name;

    @Column(unique = true)
    private Long sort = 999L;

    @Column(name = "path")
    private String path;

    private String component;

    /**
     * 类型，目录、菜单、按钮
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 权限
     */
    @Column(name = "permission")
    private String permission;

    @Column(unique = true, name = "component_name")
    private String componentName;

    private String icon;

    @Column(columnDefinition = "bit(1) default 0")
    private Boolean cache;

    @Column(columnDefinition = "bit(1) default 0")
    private Boolean hidden;

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

    /**
     * 国际化的key标识,不允许为空,当国际化环境不是zh_CN时,通过此属性获取对应的国际化值.仅国际化使用
     */
//    @NotBlank
    private String localeKey;

    // ========== 仅新增这2行（核心！解决getLabel()报错） ==========
    @Transient // 不映射数据库，仅内存存储中文，无需改表结构
    private String label; // 存储国际化后的中文（如“系统管理”）
    // ===========================================================

    public String getLocaleKey() {
        // 如果值为空检查是否存在权限值
        return isNotBlank(localeKey) ? localeKey : (isNotBlank(permission) ? permission : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}