package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.domain.EmailConfig;
import vip.efactory.domain.vo.EmailVo;
import vip.efactory.service.EmailService;

/**
 * 发送邮件
 */
@Slf4j
@RestController
@RequestMapping("api/email")
@Api(tags = "工具：邮件管理")
public class EmailController extends BaseController<EmailConfig, EmailService, Long> {

    @GetMapping
    public ResponseEntity<Object> get(){
        return new ResponseEntity<>(entityService.find(),HttpStatus.OK);
    }

    @Log("配置邮件")
    @PutMapping
    @ApiOperation("配置邮件")
    public ResponseEntity<Object> emailConfig(@Validated @RequestBody EmailConfig emailConfig){
        entityService.update(emailConfig,entityService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("发送邮件")
    @PostMapping
    @ApiOperation("发送邮件")
    public ResponseEntity<Object> send(@Validated @RequestBody EmailVo emailVo) throws Exception {
        entityService.send(emailVo,entityService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
