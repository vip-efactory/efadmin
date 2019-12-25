package vip.efactory.rest;

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
@RequestMapping("api")
public class VerificationCodeController extends BaseController<VerificationCode, VerificationCodeService, Long> {

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/code/resetEmail")
    public ResponseEntity resetEmail(@RequestBody VerificationCode code) throws Exception {
        code.setScenes(EfAdminConstant.RESET_MAIL);
        EmailVo emailVo = entityService.sendEmail(code);
        emailService.send(emailVo, emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/code/email/resetPass")
    public ResponseEntity resetPass(@RequestParam String email) throws Exception {
        VerificationCode code = new VerificationCode();
        code.setType("email");
        code.setValue(email);
        code.setScenes(EfAdminConstant.RESET_MAIL);
        EmailVo emailVo = entityService.sendEmail(code);
        emailService.send(emailVo, emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/code/validated")
    public ResponseEntity validated(VerificationCode code) {
        entityService.validated(code);
        return new ResponseEntity(HttpStatus.OK);
    }
}
