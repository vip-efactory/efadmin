package vip.efactory.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Data
public class UserDTO implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String username;

    /**
     * 用户工号---公司;用户编码---非公司场景,例如族谱管理等
     */
    private String usercode;

    private String avatar;

    private String email;

    private String phone;

    private Boolean enabled;

    @JsonIgnore
    private String password;

    private Timestamp createTime;

    private Date lastPasswordResetTime;

    @ApiModelProperty(hidden = true)
    private Set<RoleSmallDTO> roles;

    @ApiModelProperty(hidden = true)
    private JobSmallDTO job;

    private DeptSmallDTO dept;

    private Long deptId;
}
