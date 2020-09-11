package vip.efactory.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class Employee extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "{Employee.id}{property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    /**
     * 员工姓名, 在中国就是中文全名
     */
    @Length(min = 2, max = 128, message = "{Employee.name}{property.length.in.between}", groups = {Update.class, Default.class})
    @NotBlank(message = "{Employee.name}{property.not.allow.empty}", groups = {Update.class, Default.class})
    private String name;

    /**
     * 身份证号码
     */
    private String idNumber;

    /**
     * 性别，0 未知；1 男性；2女性
     */
    @Range(max = 2, message = "{Employee.sex}{property.value.in.range}", groups = {Update.class, Default.class})
    @Column(name = "sex", columnDefinition = "tinyint COMMENT '性别' ")
    private Byte sex;

    /**
     * 用户编码,用虎可以根据关联的编号,查询到用户的所有详细信息
     */
    @Length(min = 1, max = 32, message = "{Employee.code}{property.length.in.between}", groups = {Update.class, Default.class})
    @NotBlank(message = "{Employee.code}{property.not.allow.empty}", groups = {Update.class, Default.class})
    @Column(unique = true)
    private String code;

    /**
     * 员工头像
     */
    @Length(max = 2048, message = "{Employee.avatar}{property.length.in.between}", groups = {Update.class, Default.class})
    private String avatar;

    /**
     * 邮箱地址,登录用户的邮箱地址可以和这里不一样
     */
    @NotBlank(message = "{Employee.email}{property.not.allow.empty}", groups = {Update.class, Default.class})
    @Pattern(regexp = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}", message = "{Employee.email}{property.format.error}", groups = {Update.class, Default.class})
    @Column(unique = true)
    private String email;

    /**
     * 手机号
     */
    @Length(min = 3, max = 32, message = "{Employee.phone}{property.length.in.between}", groups = {Update.class, Default.class})
    @NotBlank(message = "{Employee.phone}{property.not.allow.empty}", groups = {Update.class, Default.class})
    @Column(unique = true)
    private String phone;

    /**
     * 员工状态,0--离职;1--在职;
     */
    @PositiveOrZero(message = "{Employee.status}{property.not.allow.negative}", groups = {Update.class, Default.class})
    private Integer status;

    /**
     * 生日,允许当天
     */
    @PastOrPresent(message = "{Employee.birthday}{property.value.only.past}", groups = {Update.class, Default.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    /**
     * 员工住址
     */
    @Length(min = 1, max = 256, message = "{Employee.address}{property.length.in.between}", groups = {Update.class, Default.class})
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

    public void copy(Employee source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
