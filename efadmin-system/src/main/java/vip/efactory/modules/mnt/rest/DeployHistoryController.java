package vip.efactory.modules.mnt.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
@Tag(name = "部署历史管理") // 替换@Api为@Tag
@RestController
@RequestMapping("/api/deployHistory")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class DeployHistoryController extends BaseController<DeployHistory, DeployHistoryService, String> {

    @Log("导出部署历史数据")
    @Operation(summary = "导出部署历史数据") // 替换@ApiOperation
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('deployHistory:list')")
    public void download(HttpServletResponse response, DeployHistoryQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询部署历史")
    @Operation(summary = "查询部署历史") // 替换@ApiOperation(value = "...")
    @GetMapping
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
    @Operation(summary = "多条件组合查询,返回分页数据", description = "默认每页25条记录,id字段降序") // 替换value和notes
    @PostMapping("/page")
    @PreAuthorize("@p.check('deployHistory:list')")
    public R advancedQuery(@RequestBody DeployHistory entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("删除DeployHistory")
    @Operation(summary = "删除部署历史") // 替换@ApiOperation(value = "...")
    @DeleteMapping
    @PreAuthorize("@p.check('deployHistory:del')")
    public R delete(@RequestBody Set<String> ids) {
        entityService.delete(ids);
        return R.ok();
    }
}