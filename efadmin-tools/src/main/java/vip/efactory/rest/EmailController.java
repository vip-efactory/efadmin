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
import vip.efactory.ejpa.utils.R;
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
    public R get(){
        return R.ok(entityService.find());
    }

    @Log("配置邮件")
    @PutMapping
    @ApiOperation("配置邮件")
    public R emailConfig(@Validated @RequestBody EmailConfig emailConfig){
        entityService.update(emailConfig,entityService.find());
        return R.ok();
    }

    @Log("发送邮件")
    @PostMapping
    @ApiOperation("发送邮件")
    public R send(@Validated @RequestBody EmailVo emailVo) throws Exception {
        entityService.send(emailVo,entityService.find());
        return R.ok();
    }
}
