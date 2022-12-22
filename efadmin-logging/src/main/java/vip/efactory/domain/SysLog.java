package vip.efactory.domain;

import lombok.Getter;
import lombok.Setter;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Description: 日志记录表
 *
 * @author dbdu
 * @date 19-7-10 上午10:43
 */
@Entity
@Table(name = "sys_log")
@Getter@Setter
public class SysLog extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class) // 意味着，updateById更新时id不允许为空
    private Long id;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 描述
     */
    private String description;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数
     */
    @Column(columnDefinition = "text")
    private String params;

    /**
     * 日志类型
     */
    @Column(name = "log_type")
    private String logType;

    /**
     * 请求ip
     */
    @Column(name = "request_ip")
    private String requestIp;

    /** 地址 */
    @Column(name = "address")
    private String address;

    /** 浏览器 */
    private String browser;

    /** 请求耗时 */
    private Long time;

    /** 异常详细 */
    @Column(name = "exception_detail", columnDefinition = "mediumtext")
    private byte[] exceptionDetail;

    public SysLog() {
    }

    public SysLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
