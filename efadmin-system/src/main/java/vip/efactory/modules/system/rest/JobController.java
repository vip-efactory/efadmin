package vip.efactory.modules.system.rest;

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
import vip.efactory.common.base.valid.Update;
import vip.efactory.config.DataScope;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.domain.Job;
import vip.efactory.modules.system.service.JobService;
import vip.efactory.modules.system.service.dto.JobQueryCriteria;
import vip.efactory.utils.ThrowableUtil;

import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
@Tag(name = "系统：岗位管理") // 替换@Api为@Tag
@RestController
@RequestMapping("/api/job")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class JobController extends BaseController<Job, JobService, Long> {
    private static final String ENTITY_NAME = "job";

    private final DataScope dataScope;


    @Log("导出岗位数据")
    @Operation(summary = "导出岗位数据") // 替换@ApiOperation
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('job:list')")
    public void download(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询岗位")
    @Operation(summary = "查询岗位") // 替换@ApiOperation
    @GetMapping("/page")
    @PreAuthorize("@p.check('job:list','user:list')")
    public R getJobs(JobQueryCriteria criteria, Pageable pageable) {
        // 数据权限
        criteria.setDeptIds(dataScope.getDeptIds());
        return R.ok(entityService.queryAll(criteria, pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity            含有高级查询条件
     * @param page             分页参数对象
     * @return R
     */
    @Log("分页高级查询岗位")
    @Operation(summary = "多条件组合查询,返回分页数据", description = "默认每页25条记录,id字段降序") // 替换value和notes
    @PostMapping("/page")
    @PreAuthorize("@p.check('job:list','user:list')")
    public R advancedQuery(@RequestBody Job entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("新增岗位")
    @Operation(summary = "新增岗位") // 替换@ApiOperation
    @PostMapping
    @PreAuthorize("@p.check('job:add')")
    public R create(@Validated @RequestBody Job resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return R.ok(entityService.create(resources));
    }

    @Log("修改岗位")
    @Operation(summary = "修改岗位") // 替换@ApiOperation
    @PutMapping
    @PreAuthorize("@p.check('job:edit')")
    public R update(@Validated(Update.class) @RequestBody Job resources) {
        entityService.update2(resources);
        return R.ok();
    }

    @Log("删除岗位")
    @Operation(summary = "删除岗位") // 替换@ApiOperation
    @DeleteMapping
    @PreAuthorize("@p.check('job:del')")
    public R delete(@RequestBody Set<Long> ids) {
        try {
            entityService.delete(ids);
        } catch (Exception e) {
            ThrowableUtil.throwForeignKeyException(e, "所选岗位存在用户关联，请取消关联后再试");
        }
        return R.ok();
    }
}