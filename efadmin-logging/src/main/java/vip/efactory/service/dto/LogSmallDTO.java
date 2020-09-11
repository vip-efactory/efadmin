package vip.efactory.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
