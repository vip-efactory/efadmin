package vip.efactory.modules.mnt.rest;

import com.jcraft.jsch.JSchException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.modules.mnt.domain.ServerDeploy;
import vip.efactory.modules.mnt.service.ServerDeployService;
import vip.efactory.modules.mnt.service.dto.ServerDeployQueryCriteria;

import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
@Tag(name = "服务器管理") // 替换@Api为@Tag
@RestController
@RequestMapping("/api/serverDeploy")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class ServerDeployController extends BaseController<ServerDeploy, ServerDeployService, Long> {

    @Log("导出服务器数据")
    @Operation(summary = "导出服务器数据") // 替换@ApiOperation
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('serverDeploy:list')")
    public void download(HttpServletResponse response, ServerDeployQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询服务器")
    @Operation(summary = "查询服务器") // 替换@ApiOperation(value = "...")
    @GetMapping("/page")
    @PreAuthorize("@p.check('serverDeploy:list')")
    public R getServers(ServerDeployQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAll(criteria, pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity 含有高级查询条件
     * @param page   分页参数对象
     * @return R
     */
    @Log("分页高级查询应用管理")
    @Operation(summary = "多条件组合查询,返回分页数据", description = "默认每页25条记录,id字段降序") // 替换value和notes
    @PostMapping("/page")
    @PreAuthorize("@p.check('serverDeploy:list')")
    public R advancedQuery(@RequestBody ServerDeploy entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("新增服务器")
    @Operation(summary = "新增服务器") // 替换@ApiOperation(value = "...")
    @PostMapping
    @PreAuthorize("@p.check('serverDeploy:add')")
    public R create(@Validated @RequestBody ServerDeploy resources) {
        return R.ok(entityService.create(resources));
    }

    @Log("修改服务器")
    @Operation(summary = "修改服务器") // 替换@ApiOperation(value = "...")
    @PutMapping
    @PreAuthorize("@p.check('serverDeploy:edit')")
    public R update(@Validated @RequestBody ServerDeploy resources) {
        entityService.update2(resources);
        return R.ok();
    }

    @Log("删除服务器")
    @Operation(summary = "删除Server") // 替换@ApiOperation(value = "...")
    @DeleteMapping
    @PreAuthorize("@p.check('serverDeploy:del')")
    public R delete(@RequestBody Set<Long> ids) {
        entityService.delete(ids);
        return R.ok();
    }

    @Log("测试连接服务器")
    @Operation(summary = "测试连接服务器") // 替换@ApiOperation(value = "...")
    @PostMapping("/testConnect")
    @PreAuthorize("@p.check('serverDeploy:add')")
    public R testConnect(@Validated @RequestBody ServerDeploy resources) {
        try {
            return entityService.testConnect(resources) ? R.ok() : R.error();
        } catch (JSchException e) {
            e.printStackTrace();
            return R.error(e);
        }
    }
}