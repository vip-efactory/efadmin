package vip.efactory.modules.mnt.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.utils.R;
import vip.efactory.modules.mnt.service.DeployHistoryService;
import vip.efactory.modules.mnt.service.dto.DeployHistoryQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Api(tags = "部署历史管理")
@RestController
@RequestMapping("/api/deployHistory")
public class DeployHistoryController {

    private final DeployHistoryService deployhistoryService;

    public DeployHistoryController(DeployHistoryService deployhistoryService) {
        this.deployhistoryService = deployhistoryService;
    }

    @Log("导出部署历史数据")
    @ApiOperation("导出部署历史数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('deployHistory:list')")
    public void download(HttpServletResponse response, DeployHistoryQueryCriteria criteria) throws IOException {
        deployhistoryService.download(deployhistoryService.queryAll(criteria), response);
    }

    @Log("查询部署历史")
    @ApiOperation(value = "查询部署历史")
    @GetMapping
	@PreAuthorize("@p.check('deployHistory:list')")
    public R getDeployHistorys(DeployHistoryQueryCriteria criteria, Pageable pageable){
        return R.ok(deployhistoryService.queryAll(criteria,pageable));
    }

    @Log("删除DeployHistory")
    @ApiOperation(value = "删除部署历史")
	@DeleteMapping
    @PreAuthorize("@p.check('deployHistory:del')")
    public R delete(@RequestBody Set<String> ids){
        deployhistoryService.delete(ids);
        return R.ok();
    }
}
