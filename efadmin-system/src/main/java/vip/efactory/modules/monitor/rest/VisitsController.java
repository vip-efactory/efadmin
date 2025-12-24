package vip.efactory.modules.monitor.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.efactory.common.base.utils.R;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.modules.monitor.domain.Visits;
import vip.efactory.modules.monitor.service.VisitsService;
import vip.efactory.utils.RequestHolder;

@RestController
@RequestMapping("/api/visits")
@Tag(name = "系统:访问记录管理") // 替换@Api为@Tag
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class VisitsController extends BaseController<Visits, VisitsService, Long> {

    @PostMapping
    @Operation(summary = "创建访问记录") // 替换@ApiOperation
    public R create() {
        entityService.count(RequestHolder.getHttpServletRequest());
        return R.ok();
    }

    @GetMapping
    @Operation(summary = "查询") // 替换@ApiOperation
    public R get() {
        return R.ok(entityService.get());
    }

    @GetMapping(value = "/chartData")
    @Operation(summary = "查询图表数据") // 替换@ApiOperation
    public R getChartData() {
        return R.ok(entityService.getChartData());
    }
}