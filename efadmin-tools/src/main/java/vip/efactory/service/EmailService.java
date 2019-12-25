package vip.efactory.service;


import org.springframework.scheduling.annotation.Async;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.entity.EmailConfig;
import vip.efactory.entity.vo.EmailVo;

public interface EmailService extends IBaseService<EmailConfig, Long> {

    /**
     * 更新邮件配置
     * @param emailConfig 邮件配置
     * @param old 旧的配置
     * @return EmailConfig
     */
    EmailConfig update(EmailConfig emailConfig, EmailConfig old);

    /**
     * 查询配置
     * @return EmailConfig 邮件配置
     */
    EmailConfig find();

    /**
     * 发送邮件
     * @param emailVo 邮件发送的内容
     * @param emailConfig 邮件配置
     * @throws Exception /
     */
    @Async
    void send(EmailVo emailVo, EmailConfig emailConfig) throws Exception;
}
