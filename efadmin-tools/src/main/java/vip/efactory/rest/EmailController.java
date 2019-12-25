package vip.efactory.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.entity.EmailConfig;
import vip.efactory.entity.vo.EmailVo;
import vip.efactory.service.EmailService;

/**
 * 发送邮件
 */
@Slf4j
@RestController
@RequestMapping("api")
public class EmailController extends BaseController<EmailConfig, EmailService> {

    @GetMapping(value = "/email")
    public ResponseEntity get() {
        return new ResponseEntity(entityService.find(), HttpStatus.OK);
    }

    @Log("配置邮件")
    @PutMapping(value = "/email")
    public ResponseEntity emailConfig(@Validated @RequestBody EmailConfig emailConfig) {
        entityService.update(emailConfig, entityService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("发送邮件")
    @PostMapping(value = "/email")
    public ResponseEntity send(@Validated @RequestBody EmailVo emailVo) throws Exception {
        log.warn("REST request to send Email : {}" + emailVo);
        entityService.send(emailVo, entityService.find());
        return new ResponseEntity(HttpStatus.OK);
    }
}
