package vip.efactory.service.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class LogSmallDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 请求耗时
     */
    private Long time;

    private String address;

    private String browser;

    private Timestamp createTime;
}
