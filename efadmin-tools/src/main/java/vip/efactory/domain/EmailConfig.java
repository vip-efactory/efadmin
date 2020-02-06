package vip.efactory.domain;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.ejpa.base.valid.Update;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 邮件配置类，数据存覆盖式存入数据存
 */
@Entity
@Data
@Table(name = "sys_email_config")
public class EmailConfig extends BaseEntity<Long> implements Serializable {

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
