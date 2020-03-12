package vip.efactory.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.base.valid.Update;
import vip.efactory.ejpa.utils.R;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.domain.Dict;
import vip.efactory.modules.system.domain.DictDetail;
import vip.efactory.modules.system.service.DictService;
import vip.efactory.modules.system.service.dto.DictQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = "系统：字典管理")
@RestController
@RequestMapping("/api/dict")
public class DictController extends BaseController<Dict, DictService, Long> {

    private static final String ENTITY_NAME = "dict";
    

    @Log("导出字典数据")
    @ApiOperation("导出字典数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('dict:list')")
    public void download(HttpServletResponse response, DictQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询字典")
    @ApiOperation("查询字典")
    @GetMapping(value = "/all")
    @PreAuthorize("@p.check('dict:list')")
    public R all(){
        return R.ok(entityService.queryAll(new DictQueryCriteria()));
    }

    @Log("查询字典")
    @ApiOperation("查询字典")
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
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('dict:list')")
    public R advancedQuery(@RequestBody Dict entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("新增字典")
    @ApiOperation("新增字典")
    @PostMapping
    @PreAuthorize("@p.check('dict:add')")
    public R create(@Validated @RequestBody Dict resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return R.ok(entityService.create(resources));
    }

    @Log("修改字典")
    @ApiOperation("修改字典")
    @PutMapping
    @PreAuthorize("@p.check('dict:edit')")
    public R update(@Validated(Update.class) @RequestBody Dict resources){
        entityService.update2(resources);
        return R.ok();
    }

    @Log("删除字典")
    @ApiOperation("删除字典")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@p.check('dict:del')")
    public R delete(@PathVariable Long id){
        entityService.delete(id);
        return R.ok();
    }
}
