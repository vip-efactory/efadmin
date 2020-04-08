package vip.efactory.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import vip.efactory.domain.VerificationCode;
import vip.efactory.domain.vo.EmailVo;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.utils.R;
import vip.efactory.service.EmailService;
import vip.efactory.service.VerificationCodeService;
import vip.efactory.utils.EfAdminConstant;

@AllArgsConstructor
@RestController
@RequestMapping("/api/code")
@Api(tags = "工具：验证码管理")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class VerificationCodeController extends BaseController<VerificationCode, VerificationCodeService, Long> {

    private final EmailService emailService;

    @PostMapping(value = "/resetEmail")
    @ApiOperation("重置邮箱，发送验证码")
    public R resetEmail(@RequestBody VerificationCode code) throws Exception {
        code.setScenes(EfAdminConstant.RESET_MAIL);
        EmailVo emailVo = entityService.sendEmail(code);
        emailService.send(emailVo, emailService.find());
        return R.ok();
    }

    @PostMapping(value = "/email/resetPass")
    @ApiOperation("重置密码，发送验证码")
    public R resetPass(@RequestParam String email) throws Exception {
        VerificationCode code = new VerificationCode();
        code.setType("email");
        code.setValue(email);
        code.setScenes(EfAdminConstant.RESET_MAIL);
        EmailVo emailVo = entityService.sendEmail(code);
        emailService.send(emailVo, emailService.find());
        return R.ok();
    }

    @GetMapping(value = "/validated")
    @ApiOperation("验证码验证")
    public R validated(VerificationCode code) {
        entityService.validated(code);
        return R.ok();
    }
}
