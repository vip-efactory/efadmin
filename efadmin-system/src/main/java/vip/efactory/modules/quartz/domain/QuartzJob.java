package vip.efactory.modules.quartz.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.entity.BaseEntity;

@Data
@Entity
@Table(name = "sys_quartz_job")
public class QuartzJob extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    public static final String JOB_KEY = "JOB_KEY";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    /** 定时器名称 */
    @Column(name = "job_name")
    private String jobName;

    /** Bean名称 */
    @Column(name = "bean_name")
    @NotBlank
    private String beanName;

    /** 方法名称 */
    @Column(name = "method_name")
    @NotBlank
    private String methodName;

    /** 参数 */
    @Column(name = "params")
    private String params;

    /** cron表达式 */
    @Column(name = "cron_expression")
    @NotBlank
    private String cronExpression;

    /** 状态 */
    @Column(name = "is_pause")
    private Boolean isPause = false;

    /** 备注 */
    @Column(name = "remark")
    @NotBlank
    private String remark;

//    public interface Update {
//    }
}
