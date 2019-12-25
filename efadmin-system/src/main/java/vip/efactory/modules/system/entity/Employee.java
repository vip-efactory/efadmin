package vip.efactory.modules.system.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * Description: 员工信息,详细信息,系统用户仅保留系统需要的数据
 *
 * @author dbdu
 */
@Entity
@Getter
@Setter
@Table(name = "tbl_employee")
public class Employee extends BaseEntity implements Serializable {

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long id;

    /**
     * 员工姓名, 在中国就是中文全名
     */
    @NotBlank
    private String name;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     * 用户编码,用虎可以根据关联的编号,查询到用户的所有详细信息
     */
    @NotBlank
    @Column(unique = true)
    private String code;

    /**
     * 员工头像
     */
    private String avatar;

    /**
     * 邮箱地址,登录用户的邮箱地址可以和这里不一样
     */
    @NotBlank
    @Pattern(regexp = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}", message = "格式错误")
    @Column(unique = true)
    private String email;

    /**
     * 手机号
     */
    @NotBlank
    @Column(unique = true)
    private String phone;

    /**
     * 员工状态,o--离职;1--在职;
     */
    private Integer status;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 员工住址
     */
    private String address;

    /**
     * 岗位
     */
    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

    /**
     * 部门,有兼职的情况,此处待斟酌----可以考虑利用部门和权限共同区分
     */
    @OneToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", status=" + status +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    public @interface Update {
    }

    public void copy(Employee source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
