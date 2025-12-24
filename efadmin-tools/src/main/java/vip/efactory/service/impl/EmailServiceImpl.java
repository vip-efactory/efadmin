package vip.efactory.service.impl;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
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
                // 对称加密
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return br.save(emailConfig);
    }

    @Override
    @Cacheable(key = "'1'")
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = br.findById(1L);
        return emailConfig.orElseGet(EmailConfig::new);
    }

    // ========== 核心修改：原生Jakarta Mail实现发送逻辑 ==========
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig) {
        // 1. 校验配置
        if (emailConfig == null) {
            throw new BadRequestException("请先配置邮箱信息，再操作");
        }
        if (emailVo.getTos() == null || emailVo.getTos().isEmpty()) {
            throw new BadRequestException("收件人不能为空");
        }

        // 2. 构建邮件连接属性
        Properties props = new Properties();
        props.put("mail.smtp.host", emailConfig.getHost()); // SMTP服务器地址（如smtp.qq.com/192.168.1.45）
        props.put("mail.smtp.port", emailConfig.getPort()); // 端口（465/587）
        props.put("mail.smtp.auth", "true"); // 开启认证
        props.put("mail.smtp.ssl.enable", "true"); // 开启SSL（465端口必须）
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL适配

        // 3. 解密邮箱密码/授权码
        String emailPass;
        try {
            emailPass = EncryptUtils.desDecrypt(emailConfig.getPass());
        } catch (Exception e) {
            throw new BadRequestException("邮箱密码解密失败：" + e.getMessage());
        }

        // 4. 创建邮件会话
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名（发件邮箱）+ 密码/授权码
                return new PasswordAuthentication(emailConfig.getUser(), emailPass);
            }
        });
        session.setDebug(false); // 开启调试（日志会打印邮件发送过程，方便排查）

        try {
            // 5. 构建邮件内容
            MimeMessage message = new MimeMessage(session);
            // 发件人（名称+邮箱）
            message.setFrom(new InternetAddress(emailConfig.getFromUser(), emailConfig.getUser(), "UTF-8"));
            // 收件人
            InternetAddress[] toAddresses = new InternetAddress[emailVo.getTos().size()];
            for (int i = 0; i < emailVo.getTos().size(); i++) {
                toAddresses[i] = new InternetAddress(emailVo.getTos().get(i));
            }
            message.setRecipients(Message.RecipientType.TO, toAddresses);
            // 邮件主题
            message.setSubject(emailVo.getSubject(), "UTF-8");
            // 邮件内容（HTML格式）
            message.setContent(emailVo.getContent(), "text/html;charset=UTF-8");
            // 发送时间
            message.setSentDate(new java.util.Date());

            // 6. 发送邮件
            Transport.send(message);
        } catch (Exception e) {
            // 捕获所有异常，返回明确错误
            throw new BadRequestException("邮件发送失败：" + e.getMessage());
        }
    }
}