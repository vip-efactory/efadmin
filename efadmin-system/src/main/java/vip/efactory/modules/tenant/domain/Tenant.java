package vip.efactory.modules.tenant.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.ejpa.base.valid.Update;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统租户 实体
 *
 * @author vip-efactory
 * @date 2020-04-11
 */
@Entity
@Data
@Table(name = "sys_tenant")
public class Tenant extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    /**
     * pk
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)
    @Column(name = "id")
    private Long id;

    /**
     * 租户名称
     */
    @Column(name = "tenant_name", nullable = false)
    @NotBlank(message = "{Tenant.tenantName}{property.not.allow.empty}")
    private String tenantName;

    /**
     * 租户编码
     */
    @Column(name = "tenant_code")
    private String tenantCode;

    /**
     * DB用户名
     */
    @Column(name = "db_username", nullable = false)
    @NotBlank(message = "{Tenant.dbUsername}{property.not.allow.empty}")
    private String dbUsername;

    /**
     * DB密码
     */
    @Column(name = "db_password", nullable = false)
    @NotBlank(message = "{Tenant.dbPassword}{property.not.allow.empty}")
    private String dbPassword;

    /**
     * 驱动类名
     */
    @Column(name = "driver_class_name", nullable = false)
    @NotBlank(message = "{Tenant.driverClassName}{property.not.allow.empty}")
    private String driverClassName;

    /**
     * JDBC链接
     */
    @Column(name = "jdbc_url", nullable = false)
    @NotBlank(message = "{Tenant.jdbcUrl}{property.not.allow.empty}")
    private String jdbcUrl;

    /**
     * 租户状态,0禁用；1启用等
     */
    @Column(name = "status", nullable = false)
    @NotNull(message = "{Tenant.status}{property.not.allow.empty}")
    private Integer status;

    /** 备注 */
    // remark extends from BaseEntity

    /** 创建时间 */
    // createTime extends from BaseEntity

    /** 创建人 */
    // creatorNum extends from BaseEntity

    /** 更新时间 */
    // updateTime extends from BaseEntity

    /**
     * 更新人
     */
    // updaterNum extends from BaseEntity
    public void copy(Tenant source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
