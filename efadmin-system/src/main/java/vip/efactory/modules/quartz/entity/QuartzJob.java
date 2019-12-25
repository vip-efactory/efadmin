package vip.efactory.modules.quartz.entity;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_quartz_job")
public class QuartzJob extends BaseEntity<Long> implements Serializable {

    public static final String JOB_KEY = "JOB_KEY";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = vip.efactory.ejpa.base.valid.Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    /**
     * 定时器名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * Bean名称
     */
    @Column(name = "bean_name")
    @NotBlank
    private String beanName;

    /**
     * 方法名称
     */
    @Column(name = "method_name")
    @NotBlank
    private String methodName;

    /**
     * 参数
     */
    @Column(name = "params")
    private String params;

    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    @NotBlank
    private String cronExpression;

    /**
     * 状态
     */
    @Column(name = "is_pause")
    private Boolean isPause = false;

//    /**
//     * 备注
//     */
//    @Column(name = "remark")
//    @NotBlank
//    private String remark;
//
//    /**
//     * 创建日期
//     */
//    @UpdateTimestamp
//    @Column(name = "update_time")
//    private Timestamp updateTime;

    public interface Update {
    }
}
