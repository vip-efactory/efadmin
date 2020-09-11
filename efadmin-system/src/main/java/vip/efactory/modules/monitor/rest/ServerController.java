package vip.efactory.modules.monitor.rest;

import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.modules.monitor.domain.Server;
import vip.efactory.modules.monitor.service.ServerService;
import vip.efactory.modules.monitor.service.dto.ServerQueryCriteria;

@AllArgsConstructor
@Api(tags = "服务监控管理")
@RestController
@RequestMapping("/api/server")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class ServerController extends BaseController<Server, ServerService, Integer> {

    @GetMapping("/page")
    @Log("查询服务监控")
    @ApiOperation("查询服务监控")
    @PreAuthorize("@p.check('server:list')")
    public R getServers(ServerQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAll(criteria, pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity            含有高级查询条件
     * @param page             分页参数对象
     * @return R
     */
    @Log("分页高级查询服务监控")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('server:list')")
    public R advancedQuery(@RequestBody Server entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @PostMapping
    @Log("新增服务监控")
    @ApiOperation("新增服务监控")
    @PreAuthorize("@p.check('server:add')")
    public R create(@Validated @RequestBody Server resources) {
        return R.ok(entityService.create(resources));
    }

    @PutMapping
    @Log("修改服务监控")
    @ApiOperation("修改服务监控")
    @PreAuthorize("@p.check('server:edit')")
    public R update(@Validated @RequestBody Server resources) {
        entityService.update2(resources);
        return R.ok();
    }

    @DeleteMapping
    @Log("删除服务监控")
    @ApiOperation("删除服务监控")
    @PreAuthorize("@p.check('server:del')")
    public R delete(@RequestBody Set<Integer> ids) {
        entityService.delete(ids);
        return R.ok();
    }
}
