package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.domain.GenConfig;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.utils.R;
import vip.efactory.service.GenConfigService;

@RestController
@RequestMapping("/api/genConfig")
@Api(tags = "系统：代码生成器配置管理")
public class GenConfigController extends BaseController<GenConfig, GenConfigService, Long> {

    @ApiOperation("查询")
    @GetMapping(value = "/{tableName}")
    public R get(@PathVariable String tableName) {
        return R.ok(entityService.find(tableName));
    }

    @ApiOperation("修改")
    @PutMapping
    public R emailConfig(@Validated @RequestBody GenConfig genConfig) {
        return R.ok(entityService.update(genConfig.getTableName(), genConfig));
    }
}
