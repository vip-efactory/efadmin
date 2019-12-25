package vip.efactory.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.entity.GenConfig;
import vip.efactory.service.GenConfigService;

@RestController
@RequestMapping("api")
public class GenConfigController extends BaseController<GenConfig, GenConfigService, Long> {

    /**
     * 查询生成器配置
     *
     * @return
     */
    @GetMapping(value = "/genConfig")
    public ResponseEntity get() {
        return new ResponseEntity(entityService.find(), HttpStatus.OK);
    }

    @PutMapping(value = "/genConfig")
    public ResponseEntity emailConfig(@Validated @RequestBody GenConfig genConfig) {
        return new ResponseEntity(entityService.update(genConfig), HttpStatus.OK);
    }
}
