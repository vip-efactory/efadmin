package vip.efactory.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.entity.BaseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "sys_dept")
public class Dept extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
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

    /**
     * 排序，显示时的先后位置
     */
    @NotNull(message = "排序值不能为空")
    @Schema(description = "排序值") // 替换@ApiModelProperty为@Schema
    private Integer sort;

    @JsonIgnore
    @ManyToMany(mappedBy = "depts")
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dept dept = (Dept) o;
        return Objects.equals(id, dept.id) &&
                Objects.equals(name, dept.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}