package vip.efactory.service;

import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.domain.VerificationCode;
import vip.efactory.domain.vo.EmailVo;

public interface VerificationCodeService extends IBaseService<VerificationCode, Long> {

    /**
     * 发送邮件验证码
     * @param code 验证码
     * @return EmailVo
     */
    EmailVo sendEmail(VerificationCode code);

    /**
     * 验证
     * @param code 验证码
     */
    void validated(VerificationCode code);
}
