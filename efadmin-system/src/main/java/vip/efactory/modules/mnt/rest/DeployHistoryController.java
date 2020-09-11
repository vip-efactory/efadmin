package vip.efactory.modules.mnt.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.modules.mnt.domain.DeployHistory;
import vip.efactory.modules.mnt.service.DeployHistoryService;
import vip.efactory.modules.mnt.service.dto.DeployHistoryQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
@Api(tags = "部署历史管理")
@RestController
@RequestMapping("/api/deployHistory")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class DeployHistoryController extends BaseController<DeployHistory, DeployHistoryService, String> {

    @Log("导出部署历史数据")
    @ApiOperation("导出部署历史数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('deployHistory:list')")
    public void download(HttpServletResponse response, DeployHistoryQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询部署历史")
    @ApiOperation(value = "查询部署历史")
    @GetMapping("/page")
    @PreAuthorize("@p.check('deployHistory:list')")
    public R getDeployHistorys(DeployHistoryQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAll(criteria, pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity 含有高级查询条件
     * @param page   分页参数对象
     * @return R
     */
    @Log("分页高级查询部署历史")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('deployHistory:list')")
    public R advancedQuery(@RequestBody DeployHistory entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("删除DeployHistory")
    @ApiOperation(value = "删除部署历史")
    @DeleteMapping
    @PreAuthorize("@p.check('deployHistory:del')")
    public R delete(@RequestBody Set<String> ids) {
        entityService.delete(ids);
        return R.ok();
    }
}
