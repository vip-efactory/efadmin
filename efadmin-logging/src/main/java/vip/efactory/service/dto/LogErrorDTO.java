package vip.efactory.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Description:
 *
 * @author dbdu
 * @date 19-7-10 上午10:46
 */
@Data
public class LogErrorDTO implements Serializable {

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

    /**
     * 请求ip
     */
    private String requestIp;


    /**
     * 创建日期
     */
    private Timestamp createTime;
}
