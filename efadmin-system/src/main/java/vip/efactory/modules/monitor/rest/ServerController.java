package vip.efactory.modules.monitor.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.utils.R;
import vip.efactory.modules.monitor.domain.Server;
import vip.efactory.modules.monitor.service.ServerService;
import vip.efactory.modules.monitor.service.dto.ServerQueryCriteria;

import java.util.Set;

@AllArgsConstructor
@Api(tags = "服务监控管理")
@RestController
@RequestMapping("/api/server")
public class ServerController {

    private final ServerService serverService;

    @GetMapping
    @Log("查询服务监控")
    @ApiOperation("查询服务监控")
    @PreAuthorize("@p.check('server:list')")
    public R getServers(ServerQueryCriteria criteria, Pageable pageable) {
        return R.ok(serverService.queryAll(criteria, pageable));
    }

    @PostMapping
    @Log("新增服务监控")
    @ApiOperation("新增服务监控")
    @PreAuthorize("@p.check('server:add')")
    public R create(@Validated @RequestBody Server resources) {
        return R.ok(serverService.create(resources));
    }

    @PutMapping
    @Log("修改服务监控")
    @ApiOperation("修改服务监控")
    @PreAuthorize("@p.check('server:edit')")
    public R update(@Validated @RequestBody Server resources) {
        serverService.update(resources);
        return R.ok();
    }

    @DeleteMapping
    @Log("删除服务监控")
    @ApiOperation("删除服务监控")
    @PreAuthorize("@p.check('server:del')")
    public R delete(@RequestBody Set<Integer> ids) {
        serverService.delete(ids);
        return R.ok();
    }
}
