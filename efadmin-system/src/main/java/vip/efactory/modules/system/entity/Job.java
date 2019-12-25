package vip.efactory.modules.system.entity;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(name = "sys_job")
public class Job extends BaseEntity implements Serializable {

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

    @Column(unique = true)
    @NotNull
    private Long sort;

    /**
     * 状态
     */
    @Column(name = "enabled", nullable = false)
    @NotNull
    private Boolean enabled;

    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

//    /**
//     * 创建日期
//     */
//    @Column(name = "create_time")
//    @CreationTimestamp
//    private Timestamp createTime;

    public @interface Update {
    }
}
