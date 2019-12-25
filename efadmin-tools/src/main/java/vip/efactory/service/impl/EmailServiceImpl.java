package vip.efactory.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.entity.EmailConfig;
import vip.efactory.entity.vo.EmailVo;
import vip.efactory.exception.BadRequestException;
import vip.efactory.repository.EmailRepository;
import vip.efactory.service.EmailService;
import vip.efactory.utils.EncryptUtils;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailServiceImpl extends BaseServiceImpl<EmailConfig, Long, EmailRepository> implements EmailService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig update(EmailConfig emailConfig, EmailConfig old) {
        try {
            if (!emailConfig.getPass().equals(old.getPass())) {
                // 对称加密
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return br.save(emailConfig);
    }

    @Override
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = br.findById(1L);
        if (emailConfig.isPresent()) {
            return emailConfig.get();
        } else {
            return new EmailConfig();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, EmailConfig emailConfig) {
        if (emailConfig == null) {
            throw new BadRequestException("请先配置，再操作");
        }
        /**
         * 封装
         */
        MailAccount account = new MailAccount();
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // 对称解密
            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        account.setFrom(emailConfig.getUser() + "<" + emailConfig.getFromUser() + ">");
        //ssl方式发送
        account.setStarttlsEnable(true);
        String content = emailVo.getContent();
        /**
         * 发送
         */
        try {
            Mail.create(account)
                    .setTos(emailVo.getTos().toArray(new String[emailVo.getTos().size()]))
                    .setTitle(emailVo.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
