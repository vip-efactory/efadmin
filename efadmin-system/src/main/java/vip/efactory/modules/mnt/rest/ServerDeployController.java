package vip.efactory.modules.mnt.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.utils.R;
import vip.efactory.modules.mnt.domain.ServerDeploy;
import vip.efactory.modules.mnt.service.ServerDeployService;
import vip.efactory.modules.mnt.service.dto.ServerDeployQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Api(tags = "服务器管理")
@RestController
@RequestMapping("/api/serverDeploy")
public class ServerDeployController {

    private final ServerDeployService serverDeployService;

    public ServerDeployController(ServerDeployService serverDeployService) {
        this.serverDeployService = serverDeployService;
    }

    @Log("导出服务器数据")
    @ApiOperation("导出服务器数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('serverDeploy:list')")
    public void download(HttpServletResponse response, ServerDeployQueryCriteria criteria) throws IOException {
        serverDeployService.download(serverDeployService.queryAll(criteria), response);
    }

    @Log("查询服务器")
    @ApiOperation(value = "查询服务器")
    @GetMapping
	@PreAuthorize("@p.check('serverDeploy:list')")
    public R getServers(ServerDeployQueryCriteria criteria, Pageable pageable){
    	return R.ok(serverDeployService.queryAll(criteria,pageable));
    }

    @Log("新增服务器")
    @ApiOperation(value = "新增服务器")
    @PostMapping
	@PreAuthorize("@p.check('serverDeploy:add')")
    public R create(@Validated @RequestBody ServerDeploy resources){
        return R.ok(serverDeployService.create(resources));
    }

    @Log("修改服务器")
    @ApiOperation(value = "修改服务器")
    @PutMapping
	@PreAuthorize("@p.check('serverDeploy:edit')")
    public R update(@Validated @RequestBody ServerDeploy resources){
        serverDeployService.update(resources);
        return R.ok();
    }

    @Log("删除服务器")
    @ApiOperation(value = "删除Server")
	@DeleteMapping
	@PreAuthorize("@p.check('serverDeploy:del')")
    public R delete(@RequestBody Set<Long> ids){
        serverDeployService.delete(ids);
        return R.ok();
    }

	@Log("测试连接服务器")
	@ApiOperation(value = "测试连接服务器")
	@PostMapping("/testConnect")
	@PreAuthorize("@p.check('serverDeploy:add')")
	public R testConnect(@Validated @RequestBody ServerDeploy resources){
		return R.ok(serverDeployService.testConnect(resources));
	}
}
