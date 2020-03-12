package vip.efactory.modules.quartz.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.base.valid.Update;
import vip.efactory.ejpa.utils.R;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.quartz.domain.QuartzJob;
import vip.efactory.modules.quartz.service.QuartzJobService;
import vip.efactory.modules.quartz.service.dto.JobQueryCriteria;
import vip.efactory.modules.system.domain.Dict;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Slf4j
@RestController
@Api(tags = "系统:定时任务管理")
@RequestMapping("/api/jobs")
public class QuartzJobController extends BaseController<QuartzJob, QuartzJobService, Long> {

    private static final String ENTITY_NAME = "quartzJob";

    @Log("查询定时任务")
    @ApiOperation("查询定时任务")
    @GetMapping("/page")
    @PreAuthorize("@p.check('timing:list')")
    public R getJobs(JobQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAll(criteria, pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity            含有高级查询条件
     * @param page             分页参数对象
     * @return R
     */
    @Log("分页高级查询定时任务")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('timing:list')")
    public R advancedQuery(@RequestBody QuartzJob entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("导出任务数据")
    @ApiOperation("导出任务数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('timing:list')")
    public void download(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("导出日志数据")
    @ApiOperation("导出日志数据")
    @GetMapping(value = "/logs/download")
    @PreAuthorize("@p.check('timing:list')")
    public void downloadLog(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        entityService.downloadLog(entityService.queryAllLog(criteria), response);
    }

    @ApiOperation("查询任务执行日志")
    @GetMapping(value = "/logs")
    @PreAuthorize("@p.check('timing:list')")
    public R getJobLogs(JobQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAllLog(criteria, pageable));
    }

    @Log("新增定时任务")
    @ApiOperation("新增定时任务")
    @PostMapping
    @PreAuthorize("@p.check('timing:add')")
    public R create(@Validated @RequestBody QuartzJob resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return R.ok(entityService.create(resources));
    }

    @Log("修改定时任务")
    @ApiOperation("修改定时任务")
    @PutMapping
    @PreAuthorize("@p.check('timing:edit')")
    public R update(@Validated(Update.class) @RequestBody QuartzJob resources) {
        entityService.update2(resources);
        return R.ok();
    }

    @Log("更改定时任务状态")
    @ApiOperation("更改定时任务状态")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@p.check('timing:edit')")
    public R updateIsPause(@PathVariable Long id) {
        entityService.updateIsPause(entityService.findById2(id));
        return R.ok();
    }

    @Log("执行定时任务")
    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    @PreAuthorize("@p.check('timing:edit')")
    public R execution(@PathVariable Long id) {
        entityService.execution(entityService.findById2(id));
        return R.ok();
    }

    @Log("删除定时任务")
    @ApiOperation("删除定时任务")
    @DeleteMapping
    @PreAuthorize("@p.check('timing:del')")
    public R delete(@RequestBody Set<Long> ids) {
        entityService.delete(ids);
        return R.ok();
    }
}
