package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.entity.VerificationCode;
import vip.efactory.entity.vo.EmailVo;
import vip.efactory.service.EmailService;
import vip.efactory.service.VerificationCodeService;
import vip.efactory.utils.EfAdminConstant;

@RestController
@RequestMapping("/api/code")
@Api(tags = "工具：验证码管理")
public class VerificationCodeController extends BaseController<VerificationCode, VerificationCodeService, Long> {

    private final EmailService emailService;

    public VerificationCodeController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/resetEmail")
    @ApiOperation("重置邮箱，发送验证码")
    public ResponseEntity<Object> resetEmail(@RequestBody VerificationCode code) throws Exception {
        code.setScenes(EfAdminConstant.RESET_MAIL);
        EmailVo emailVo = entityService.sendEmail(code);
        emailService.send(emailVo,emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/email/resetPass")
    @ApiOperation("重置密码，发送验证码")
    public ResponseEntity<Object> resetPass(@RequestParam String email) throws Exception {
        VerificationCode code = new VerificationCode();
        code.setType("email");
        code.setValue(email);
        code.setScenes(EfAdminConstant.RESET_MAIL);
        EmailVo emailVo = entityService.sendEmail(code);
        emailService.send(emailVo,emailService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/validated")
    @ApiOperation("验证码验证")
    public ResponseEntity<Object> validated(VerificationCode code){
        entityService.validated(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
