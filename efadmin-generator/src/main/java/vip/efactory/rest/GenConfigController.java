package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.domain.GenConfig;
import vip.efactory.service.GenConfigService;

@RestController
@RequestMapping("/api/genConfig")
@Api(tags = "系统：代码生成器配置管理")
public class GenConfigController extends BaseController<GenConfig, GenConfigService, Long> {

    @ApiOperation("查询")
    @GetMapping(value = "/{tableName}")
    public ResponseEntity<Object> get(@PathVariable String tableName){
        return new ResponseEntity<>(entityService.find(tableName), HttpStatus.OK);
    }

    @ApiOperation("修改")
    @PutMapping
    public ResponseEntity<Object> emailConfig(@Validated @RequestBody GenConfig genConfig){
        return new ResponseEntity<>(entityService.update(genConfig.getTableName(), genConfig),HttpStatus.OK);
    }
}
