package vip.efactory.modules.mnt.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Data
public class ServerDeployDto implements Serializable {

    private Long id;

    private String name;

    private String ip;

    private Integer port;

    private String account;

    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
