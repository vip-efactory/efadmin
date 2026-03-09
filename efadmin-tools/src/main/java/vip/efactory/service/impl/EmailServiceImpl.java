package vip.efactory.service.impl;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j; // 1. 新增日志注解
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.domain.EmailConfig;
import vip.efactory.domain.vo.EmailVo;
import vip.efactory.exception.BadRequestException;
import vip.efactory.repository.EmailRepository;
import vip.efactory.service.EmailService;
import vip.efactory.utils.EncryptUtils;

import java.util.Optional;
import java.util.Properties;

@Slf4j // 新增：日志注解，替代e.printStackTrace()
@Service
@CacheConfig(cacheNames = "email")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailServiceImpl extends BaseServiceImpl<EmailConfig, Long, EmailRepository> implements EmailService {

    @Override
    @CachePut(key = "'1'")
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig update(EmailConfig emailConfig, EmailConfig old) {
        try {
            if(!emailConfig.getPass().equals(old.getPass())){
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            log.error("邮箱密码加密失败", e); // 替换e.printStackTrace()
            throw new BadRequestException("邮箱配置失败：密码加密失败"); // 抛出异常终止执行
        }
        return br.save(emailConfig);
    }

    @Override
    @Cacheable(key = "'1'")
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = br.findById(1L);
        return emailConfig.orElseGet(EmailConfig::new);
    }

    // ========== 核心修改：补全非空校验+修复次要问题 ==========
    @Override
    // 2. 移除无意义的@Transactional，异步+事务无意义且可能导致上下文异常
    public void send(EmailVo emailVo, EmailConfig emailConfig) throws Exception {
        // 1. 校验配置对象非空
        if (emailConfig == null) {
            throw new BadRequestException("请先配置邮箱信息，再操作");
        }
        // 2. 核心：校验配置对象的属性非空（彻底避免NPE的关键）
        checkEmailConfigProperties(emailConfig);
        // 3. 校验收件人非空
        if (emailVo.getTos() == null || emailVo.getTos().isEmpty()) {
            throw new BadRequestException("收件人不能为空");
        }

        // 构建邮件连接属性
        Properties props = new Properties();
        props.put("mail.smtp.host", emailConfig.getHost());
        props.put("mail.smtp.port", emailConfig.getPort());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.class", "jakarta.net.ssl.SSLSocketFactory"); // 已适配Jakarta，无需改

        // 解密密码/授权码
        String emailPass;
        try {
            emailPass = EncryptUtils.desDecrypt(emailConfig.getPass());
        } catch (Exception e) {
            log.error("邮箱密码解密失败", e);
            throw new BadRequestException("邮箱密码解密失败：" + e.getMessage());
        }

        // 创建邮件会话
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUser(), emailPass);
            }
        });
        session.setDebug(true); // 3. 开启debug，方便排查邮件发送底层问题

        try {
            // 构建邮件内容
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailConfig.getFromUser(), emailConfig.getUser(), "UTF-8"));
            InternetAddress[] toAddresses = new InternetAddress[emailVo.getTos().size()];
            for (int i = 0; i < emailVo.getTos().size(); i++) {
                toAddresses[i] = new InternetAddress(emailVo.getTos().get(i));
            }
            message.setRecipients(Message.RecipientType.TO, toAddresses);
            message.setSubject(emailVo.getSubject(), "UTF-8");
            message.setContent(emailVo.getContent(), "text/html;charset=UTF-8");
            message.setSentDate(new java.util.Date());

            // 发送邮件
            Transport.send(message);
            log.info("邮件发送成功，收件人：{}，主题：{}", emailVo.getTos(), emailVo.getSubject());
        } catch (Exception e) {
            log.error("邮件发送失败", e);
            throw new BadRequestException("邮件发送失败：" + e.getMessage());
        }
    }

    // 新增：EmailConfig属性非空校验方法，抽离成独立方法，复用+易维护
    private void checkEmailConfigProperties(EmailConfig config) {
        if (config.getHost() == null || config.getHost().isBlank()) {
            throw new BadRequestException("邮箱配置未设置SMTP服务器地址");
        }
        if (config.getPort() == null || config.getPort().isBlank()) {
            throw new BadRequestException("邮箱配置未设置SMTP端口");
        }
        if (config.getUser() == null || config.getUser().isBlank()) {
            throw new BadRequestException("邮箱配置未设置发件人账号");
        }
        if (config.getPass() == null || config.getPass().isBlank()) {
            throw new BadRequestException("邮箱配置未设置发件人密码/授权码");
        }
        if (config.getFromUser() == null || config.getFromUser().isBlank()) {
            throw new BadRequestException("邮箱配置未设置发件人邮箱地址");
        }
    }
}