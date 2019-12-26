package vip.efactory.modules.quartz.domain;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.ejpa.base.valid.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(name = "sys_quartz_log")
public class QuartzLog extends BaseEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    /** 任务名称 */
    @Column(name = "job_name")
    private String jobName;

    /** Bean名称 */
    @Column(name = "baen_name")
    private String beanName;

    /** 方法名称 */
    @Column(name = "method_name")
    private String methodName;

    /** 参数 */
    @Column(name = "params")
    private String params;

    /** cron表达式 */
    @Column(name = "cron_expression")
    private String cronExpression;

    /** 状态 */
    @Column(name = "is_success")
    private Boolean isSuccess;

    /** 异常详细 */
    @Column(name = "exception_detail",columnDefinition = "text")
    private String exceptionDetail;

    /** 耗时（毫秒） */
    private Long time;

//    /**
//     * 创建日期
//     */
//    @CreationTimestamp
//    @Column(name = "create_time")
//    private Timestamp createTime;
}
