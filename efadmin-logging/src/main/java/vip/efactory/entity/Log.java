package vip.efactory.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Description: 日志记录表
 *
 * @author dbdu
 * @date 19-7-10 上午10:43
 */
@Entity
@Data
@Table(name = "sys_log")
@NoArgsConstructor
public class Log extends BaseEntity implements Serializable {

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

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 异常详细
     */
    @Column(name = "exception_detail", columnDefinition = "text")
    private byte[] exceptionDetail;

    public Log(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
