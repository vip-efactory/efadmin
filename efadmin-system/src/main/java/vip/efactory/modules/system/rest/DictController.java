package vip.efactory.modules.system.rest;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.controller.BaseController;

import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.domain.Dict;
import vip.efactory.modules.system.service.DictService;
import vip.efactory.modules.system.service.dto.DictQueryCriteria;

@Tag(name = "系统：字典管理") // 替换@Api为@Tag
@RestController
@RequestMapping("/api/dict")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class DictController extends BaseController<Dict, DictService, Long> {

    private static final String ENTITY_NAME = "dict";


    @Log("导出字典数据")
    @Operation(summary = "导出字典数据") // 替换@ApiOperation
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('dict:list')")
    public void download(HttpServletResponse response, DictQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询字典")
    @Operation(summary = "查询字典") // 替换@ApiOperation
    @GetMapping(value = "/all")
    @PreAuthorize("@p.check('dict:list')")
    public R all(){
        return R.ok(entityService.queryAll(new DictQueryCriteria()));
    }

    @Log("查询字典")
    @Operation(summary = "查询字典") // 替换@ApiOperation
    @GetMapping("/page")
    @PreAuthorize("@p.check('dict:list')")
    public R getDicts(DictQueryCriteria resources, Pageable pageable){
        return R.ok(entityService.queryAll(resources,pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity            含有高级查询条件
     * @param page             分页参数对象
     * @return R
     */
    @Log("分页高级查询Dict")
    @Operation(summary = "多条件组合查询,返回分页数据", description = "默认每页25条记录,id字段降序") // 替换value和notes
    @PostMapping("/page")
    @PreAuthorize("@p.check('dict:list')")
    public R advancedQuery(@RequestBody Dict entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("新增字典")
    @Operation(summary = "新增字典") // 替换@ApiOperation
    @PostMapping
    @PreAuthorize("@p.check('dict:add')")
    public R create(@Validated @RequestBody Dict resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return R.ok(entityService.create(resources));
    }

    @Log("修改字典")
    @Operation(summary = "修改字典") // 替换@ApiOperation
    @PutMapping
    @PreAuthorize("@p.check('dict:edit')")
    public R update(@Validated(Update.class) @RequestBody Dict resources){
        entityService.update2(resources);
        return R.ok();
    }

    @Log("删除字典")
    @Operation(summary = "删除字典") // 替换@ApiOperation
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@p.check('dict:del')")
    public R delete(@Parameter(name = "id", description = "字典ID", required = true) @PathVariable Long id){
        entityService.delete(id);
        return R.ok();
    }
}