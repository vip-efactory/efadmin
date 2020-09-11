package vip.efactory.modules.tenant.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
* 系统租户 DTO
* @author vip-efactory
* @date 2020-04-11
*/
@Data
public class TenantDto implements Serializable {

    /** pk */
    private Long id;

    /** 租户名称 */
    private String tenantName;

    /** 租户编码 */
    private String tenantCode;

    /** DB用户名 */
    private String dbUsername;

    /** DB密码 */
    private String dbPassword;

    /** 驱动类名 */
    private String driverClassName;

    /** JDBC链接 */
    private String jdbcUrl;

    /** 租户状态,-1禁用；0正常等 */
    private Integer status;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 创建人 */
    private String creatorNum;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /** 更新人 */
    private String updaterNum;
}
