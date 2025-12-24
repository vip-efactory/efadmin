package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.domain.LocalStorage;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.service.LocalStorageService;
import vip.efactory.service.dto.LocalStorageQueryCriteria;
import vip.efactory.utils.SecurityUtils;


import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
@Api(tags = "工具：本地存储管理")
@RestController
@RequestMapping("/api/localStorage")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
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

    @Log("使用Ids删除LocalStorage")
    @DeleteMapping(value = {"/", ""}) // 对齐路径配置，兼容前端请求
    @ApiOperation("依据Ids来删除LocalStorage对应的记录")
    @PreAuthorize("@p.check('storage:del')")
    public R deleteByIds(@RequestBody Set<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return R.error("删除失败：ID集合不能为空");
            }
            // 循环调用单条删除，复用单删逻辑
            for (Long id : ids) {
                entityService.delete(id); // 调用单删方法（需在Service新增）
            }
            return R.ok("删除成功");
        } catch (Exception e) {
            return R.error("删除失败：" + e.getMessage());
        }
    }
}
