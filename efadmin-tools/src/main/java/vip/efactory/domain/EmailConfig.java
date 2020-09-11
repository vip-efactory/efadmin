package vip.efactory.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.entity.BaseEntity;

/**
 * 邮件配置类，数据存覆盖式存入数据存
 */
@Entity
@Data
@Table(name = "sys_email_config")
public class EmailConfig extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    /**
     * 邮件服务器SMTP地址
     */
    @NotBlank
    private String host;

    /**
     * 邮件服务器SMTP端口
     */
    @NotBlank
    private String port;

    /**
     * 发件者用户名，默认为发件人邮箱前缀
     */
    @NotBlank
    private String user;

    @NotBlank
    private String pass;

    /**
     * 发件人邮箱地址
     */
    @NotBlank
    @Column(name = "from_user")
    private String fromUser;
}
