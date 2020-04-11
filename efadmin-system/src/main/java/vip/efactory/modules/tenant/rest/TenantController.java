package vip.efactory.modules.tenant.rest;

import vip.efactory.aop.log.Log;
import vip.efactory.modules.tenant.domain.Tenant;
import vip.efactory.modules.tenant.service.ITenantService;
import vip.efactory.modules.tenant.service.dto.TenantQueryCriteria;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.base.controller.EPage;
import vip.efactory.ejpa.utils.R;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* 系统租户 控制器层
* @author vip-efactory
* @date 2020-04-11
*/
@Api(tags = "系统租户管理")
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
    @ApiOperation(value = "GET获取分页数据", notes = "默认每页25条记录,id字段降序")
    @GetMapping("/page")
    @PreAuthorize("@p.check('tenant:list')")
    public R getByPage(TenantQueryCriteria criteria, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return R.ok(new EPage(entityService.queryAll(criteria, page)));
    }

    /**
     * Description: 多条件高级查询;默认的分页与排序,默认id降序
     *
     * @param baseEntity 含有高级搜索条件的基础对象
     * @param page 框架默认的分页对象
     * @return R
     */
    @Log("POST高级查询系统租户")
    @ApiOperation(value = "POST多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
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
    @ApiOperation(value = "多字段模糊查询,例如:q=abc&fields=name,address,desc", notes = "多个字段模糊匹配")
    @RequestMapping(value = "/fuzzy", method = {RequestMethod.GET})
    @PreAuthorize("@p.check('tenant:list')")
    public R getByPage(@RequestParam String q, @RequestParam String fields, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.queryMutiField(q, fields, page);
    }


    /**
     * Description: 使用id来获取实体
     *
     * @param id 主键
     * @return R
     */
    @Log("依ID查询系统租户")
    @GetMapping("/{id}")
    @ApiOperation(value = "依据id来获取对应的记录", notes = "依据id来获取对应的记录")
    @PreAuthorize("@p.check('tenant:list')")
    public R getById(@PathVariable("id") Long id) {
        return super.getById(id);
    }


    /**
     * Description: 保存实体
     *
     * @param entity 要保存的对象
     * @return R
     */
    @Log("新增系统租户")
    @PostMapping
    @ApiOperation(value = "保存记录", notes = "保存来自json格式对象")
    @PreAuthorize("@p.check('tenant:add')")
    public R save(@RequestBody @ApiParam(name = "entity", value = "Json格式", required = true) Tenant entity) {
        return super.save(entity);
    }

    /**
     * Description: 依据id更新数据库,不用空值更新已存在的值
     *
     * @param entity 将更新的对象
     * @return R
     */
    @Log("依ID修改系统租户")
    @PutMapping
    @ApiOperation(value = "依据id来更新对应的记录", notes = "依据id来更新对应的记录,属性值为空则不更新数据表中已有的数据")
    @PreAuthorize("@p.check('tenant:edit')")
    public R updateById(@RequestBody @ApiParam(name = "entity", value = "Json格式", required = true) Tenant entity) {
        return super.updateById(entity);
    }

    /**
     * Description: 依据id来删除实体
     *
     * @param id 主键
     * @return R
     */
    @Log("依ID删除系统租户")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "依据id来删除对应的记录", notes = "依据id来删除对应的记录")
    @PreAuthorize("@p.check('tenant:del')")
    public R deleteById(@PathVariable("id") Long id) {
        return super.deleteById(id);
    }

}
