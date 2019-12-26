package vip.efactory.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author dbdu
 * @date 2019-07-21
 */
@Data
public class EmployeeDto implements Serializable {

    // key
    private Long id;

    // 住址
    private String address;

    // 头像
    private String avatar;

    // 生日
    private Date birthday;

    // 编号
    private String code;

    // 邮箱
    private String email;

    // 身份证号
    private String idNumber;

    // 中文姓名
    private String name;

    // 手机号
    private String phone;

    // 状态
    private Integer status;

    // 部门id
    private Long deptId;

    // 职位id
    private Long jobId;

    // 备注
    private String remark;

    // 创建时间
    private Date createTime;

    // 创建人
    private String creatorNum;

    // 更新时间
    private Date updateTime;

    // 更新人
    private String updaterNum;
}
