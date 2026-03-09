package vip.efactory.modules.tenant.rest;

import vip.efactory.aop.log.Log;
import vip.efactory.common.base.page.EPage;
import vip.efactory.common.base.utils.R;
import vip.efactory.modules.tenant.domain.Tenant;
import vip.efactory.modules.tenant.service.ITenantService;
import vip.efactory.modules.tenant.service.dto.TenantQueryCriteria;
import vip.efactory.ejpa.base.controller.BaseController;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 系统租户 控制器层
 * @author vip-efactory
 * @date 2020-04-11
 */
@Tag(name = "系统租户管理") // 替换@Api为@Tag
@RestController
@RequestMapping("api/tenant")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class TenantController extends BaseController<Tenant, ITenantService, Long> {

    /**
     * Description: 默认的分页与排序,默认id降序
     *
     * @param page 框架默认的分页对象
     * @return R
     */
    @Log("GET分页查询系统租户")
    @Operation(summary = "GET获取分页数据", description = "默认每页25条记录,id字段降序") // 替换@ApiOperation
    @GetMapping("/page")
    @PreAuthorize("@p.check('tenant:list')")
    public R getByPage(TenantQueryCriteria criteria, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return R.ok(new EPage(entityService.queryAll(criteria, page)));
    }

    /**
     * Description: 多条件高级查询;默认的分页与排序,默认id降序
     *
     * @param entity 含有高级搜索条件的基础对象
     * @param page 框架默认的分页对象
     * @return R
     */
    @Log("POST高级查询系统租户")
    @Operation(summary = "POST多条件组合查询,返回分页数据", description = "默认每页25条记录,id字段降序") // 替换@ApiOperation
    @PostMapping("/page")
    @PreAuthorize("@p.check('tenant:list')")
    public R advancedQuery(@RequestBody Tenant entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    /**
     * Description: 多字段模糊查询,默认id降序,例如:
     * http://localhost:8000/api/tenant/fuzzy?fields=name,version&q=BB   仅供事例作用
     *
     * @param q 要匹配的值
     * @param fields 要匹配哪些字段
     * @param page 默认的分页对象
     * @return R
     */
    @Log("多字段模糊查询系统租户")
    @Operation(summary = "多字段模糊查询,例如:q=abc&fields=name,address,desc", description = "多个字段模糊匹配") // 替换@ApiOperation
    @RequestMapping(value = "/fuzzy", method = {RequestMethod.GET})
    @PreAuthorize("@p.check('tenant:list')")
    public R getByPage(
            @Parameter(name = "q", description = "要匹配的值", required = true) @RequestParam String q,
            @Parameter(name = "fields", description = "要匹配哪些字段", required = true) @RequestParam String fields,
            @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.queryMultiField(q, fields, page);
    }


    /**
     * Description: 使用id来获取实体
     *
     * @param id 主键
     * @return R
     */
    @Override
    @Log("依ID查询系统租户")
    @GetMapping("/{id}")
    @Operation(summary = "依据id来获取对应的记录", description = "依据id来获取对应的记录") // 替换@ApiOperation
    @PreAuthorize("@p.check('tenant:list')")
    public R getById(@Parameter(name = "id", description = "主键", required = true) @PathVariable("id") Long id) {
        return super.getById(id);
    }


    /**
     * Description: 保存实体
     *
     * @param entity 要保存的对象
     * @return R
     */
    @Override
    @Log("新增系统租户")
    @PostMapping
    @Operation(summary = "保存记录", description = "保存来自json格式对象") // 替换@ApiOperation
    @PreAuthorize("@p.check('tenant:add')")
    public R save(@Parameter(name = "entity", description = "Json格式", required = true) @RequestBody Tenant entity) {
        entityService.save(entity);
        return R.ok();
    }

    /**
     * Description: 依据id更新数据库,不用空值更新已存在的值
     *
     * @param entity 将更新的对象
     * @return R
     */
    @Override
    @Log("依ID修改系统租户")
    @PutMapping
    @Operation(summary = "依据id来更新对应的记录", description = "依据id来更新对应的记录,属性值为空则不更新数据表中已有的数据") // 替换@ApiOperation
    @PreAuthorize("@p.check('tenant:edit')")
    public R updateById(@Parameter(name = "entity", description = "Json格式", required = true) @RequestBody Tenant entity) {
        entityService.update(entity);
        return R.ok();
    }

    /**
     * Description: 依据id来删除实体
     *
     * @param id 主键
     * @return R
     */
    @Override
    @Log("依ID删除系统租户")
    @DeleteMapping("/{id}")
    @Operation(summary = "依据id来删除对应的记录", description = "依据id来删除对应的记录") // 替换@ApiOperation
    @PreAuthorize("@p.check('tenant:del')")
    public R deleteById(@Parameter(name = "id", description = "主键", required = true) @PathVariable("id") Long id) {
        return super.deleteById(id);
    }

}