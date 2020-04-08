package vip.efactory.modules.mnt.rest;

import com.jcraft.jsch.JSchException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.utils.R;
import vip.efactory.modules.mnt.domain.ServerDeploy;
import vip.efactory.modules.mnt.service.ServerDeployService;
import vip.efactory.modules.mnt.service.dto.ServerDeployQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
@Api(tags = "服务器管理")
@RestController
@RequestMapping("/api/serverDeploy")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class ServerDeployController extends BaseController<ServerDeploy, ServerDeployService, Long> {

    @Log("导出服务器数据")
    @ApiOperation("导出服务器数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('serverDeploy:list')")
    public void download(HttpServletResponse response, ServerDeployQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询服务器")
    @ApiOperation(value = "查询服务器")
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
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('serverDeploy:list')")
    public R advancedQuery(@RequestBody ServerDeploy entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("新增服务器")
    @ApiOperation(value = "新增服务器")
    @PostMapping
    @PreAuthorize("@p.check('serverDeploy:add')")
    public R create(@Validated @RequestBody ServerDeploy resources) {
        return R.ok(entityService.create(resources));
    }

    @Log("修改服务器")
    @ApiOperation(value = "修改服务器")
    @PutMapping
    @PreAuthorize("@p.check('serverDeploy:edit')")
    public R update(@Validated @RequestBody ServerDeploy resources) {
        entityService.update2(resources);
        return R.ok();
    }

    @Log("删除服务器")
    @ApiOperation(value = "删除Server")
    @DeleteMapping
    @PreAuthorize("@p.check('serverDeploy:del')")
    public R delete(@RequestBody Set<Long> ids) {
        entityService.delete(ids);
        return R.ok();
    }

    @Log("测试连接服务器")
    @ApiOperation(value = "测试连接服务器")
    @PostMapping("/testConnect")
    @PreAuthorize("@p.check('serverDeploy:add')")
    public R testConnect(@Validated @RequestBody ServerDeploy resources) {
        try {
            return entityService.testConnect(resources) == true ? R.ok() : R.error();
        } catch (JSchException e) {
            e.printStackTrace();
            return R.error(e);
        }
    }
}
