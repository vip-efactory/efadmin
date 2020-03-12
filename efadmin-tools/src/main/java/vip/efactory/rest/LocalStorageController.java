package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.aop.log.Log;
import vip.efactory.domain.LocalStorage;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.utils.R;
import vip.efactory.service.LocalStorageService;
import vip.efactory.service.dto.LocalStorageQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Api(tags = "工具：本地存储管理")
@RestController
@RequestMapping("/api/localStorage")
public class LocalStorageController extends BaseController<LocalStorage, LocalStorageService, Long> {

    @ApiOperation("查询文件")
    @GetMapping("/page")
    @PreAuthorize("@p.check('storage:list')")
    public R getLocalStorages(LocalStorageQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAll(criteria, pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity 含有高级查询条件
     * @param page   分页参数对象
     * @return R
     */
    @Log("分页高级查询本地存储")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('storage:list')")
    public R advancedQuery(@RequestBody LocalStorage entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('storage:list')")
    public void download(HttpServletResponse response, LocalStorageQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @ApiOperation("上传文件")
    @PostMapping
    @PreAuthorize("@p.check('storage:add')")
    public R create(@RequestParam String name, @RequestParam("file") MultipartFile file) {
        return R.ok(entityService.create(name, file));    // 原http的状态码是已创建！
    }

    @ApiOperation("修改文件")
    @PutMapping
    @PreAuthorize("@p.check('storage:edit')")
    public R update(@Validated @RequestBody LocalStorage resources) {
        entityService.update2(resources);
        return R.ok();  // 原HttpStatus.NO_CONTENT
    }

    @Log("多选删除")
    @DeleteMapping
    @ApiOperation("多选删除")
    public R deleteAll(@RequestBody Long[] ids) {
        entityService.deleteAll(ids);
        return R.ok();
    }
}
