package vip.efactory.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * Description:
 *
 * @author dbdu
 * @date 19-7-10 上午10:46
 */
@Data
public class LogErrorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private String params;

    private String browser;

    private String requestIp;

    private String address;

    /**
     * 创建日期
     */
    private Timestamp createTime;
}
