package vip.efactory.rest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vip.efactory.aop.log.Log;
import vip.efactory.domain.EmailConfig;
import vip.efactory.domain.vo.EmailVo;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.utils.R;
import vip.efactory.service.EmailService;

/**
 * 发送邮件
 */
@RestController
@RequestMapping("api/email")
@Api(tags = "工具：邮件管理")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
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
