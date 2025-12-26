package vip.efactory.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


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
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

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

    // 部门名称
    private String deptName;

    // 职位名称
    private String jobName;

    // 备注
    private String remark;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    // 创建人
    private String creatorNum;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    // 更新人
    private String updaterNum;

    // 最小内部类，用于序列化 dept/job 嵌套对象
    public static class SimplePair implements Serializable {
        private final Long id;
        private final String name;
        public SimplePair(Long id, String name) { this.id = id; this.name = name; }
        public Long getId() { return id; }
        public String getName() { return name; }
    }

    // 计算属性：序列化为嵌套的 dept 对象
    public SimplePair getDept() {
        // 若两者都为空则不输出嵌套对象
        if (this.deptId == null && (this.deptName == null || this.deptName.isEmpty())) {
            return null;
        }
        return new SimplePair(this.deptId, this.deptName);
    }

    // 计算属性：序列化为嵌套的 job 对象
    public SimplePair getJob() {
        if (this.jobId == null && (this.jobName == null || this.jobName.isEmpty())) {
            return null;
        }
        return new SimplePair(this.jobId, this.jobName);
    }
}
