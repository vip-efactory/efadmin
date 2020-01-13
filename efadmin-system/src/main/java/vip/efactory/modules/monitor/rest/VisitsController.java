package vip.efactory.modules.monitor.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.utils.R;
import vip.efactory.modules.monitor.domain.Visits;
import vip.efactory.modules.monitor.service.VisitsService;
import vip.efactory.utils.RequestHolder;

@RestController
@RequestMapping("/api/visits")
@Api(tags = "系统:访问记录管理")
public class VisitsController extends BaseController<Visits, VisitsService, Long> {

    @PostMapping
    @ApiOperation("创建访问记录")
    public R create() {
        entityService.count(RequestHolder.getHttpServletRequest());
        return R.ok();
    }

    @GetMapping
    @ApiOperation("查询")
    public R get() {
        return R.ok(entityService.get());
    }

    @GetMapping(value = "/chartData")
    @ApiOperation("查询图表数据")
    public R getChartData() {
        return R.ok(entityService.getChartData());
    }
}
