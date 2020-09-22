package ${package}.rest;

import vip.efactory.aop.log.Log;
import ${package}.domain.${className};
import ${package}.service.I${className}Service;
import ${package}.service.dto.${className}QueryCriteria;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.base.controller.EPage;
import vip.efactory.ejpa.utils.R;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.Set;

/**
* ${apiAlias} 控制器层
* @author ${author}
* @date ${date}
*/
@Api(tags = "${apiAlias}管理")
@RestController
@RequestMapping("api/${changeClassName}")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class ${className}Controller extends BaseController<${className}, I${className}Service, ${pkColumnType}> {

    /**
     * Description: 默认的分页与排序,默认${pkChangeColName}降序
     *
     * @param criteria 查询条件对象
     * @param page     框架默认的分页对象
     * @return R
     */
    @Log("GET分页查询${apiAlias}")
    @ApiOperation(value = "GET获取分页数据", notes = "默认每页25条记录,${pkChangeColName}字段降序")
    @GetMapping("/page")
    @PreAuthorize("@p.check('${changeClassName}:list')")
    public R getByPage(${className}QueryCriteria criteria, @PageableDefault(value = 25, sort = {"${pkChangeColName}"}, direction = Sort.Direction.DESC) Pageable page) {
        return R.ok(new EPage(entityService.queryAll(criteria, page)));
    }

    /**
     * Description: 多条件高级查询;默认的分页与排序,默认${pkChangeColName}降序
     *
     * @param entity 含有高级搜索条件的基础对象
     * @param page 框架默认的分页对象
     * @return R
     */
    @Log("POST高级查询${apiAlias}")
    @ApiOperation(value = "POST多条件组合查询,返回分页数据", notes = "默认每页25条记录,${pkChangeColName}字段降序")
    @PostMapping("/query")
    @PreAuthorize("@p.check('${changeClassName}:list')")
    public R advancedQuery(@RequestBody ${className} entity, @PageableDefault(value = 25, sort = {"${pkChangeColName}"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    /**
     * Description: 多字段模糊查询,默认${pkChangeColName}降序,例如:
     * http://localhost:8000/api/${changeClassName}/fuzzy?fields=name,version&q=BB   仅供事例作用
     *
     * @param q 要匹配的值
     * @param fields 要匹配哪些字段
     * @param page 默认的分页对象
     * @return R
     */
    @Log("多字段模糊查询${apiAlias}")
    @ApiOperation(value = "多字段模糊查询,例如:q=abc&fields=name,address,desc", notes = "多个字段模糊匹配")
    @RequestMapping(value = "/fuzzy", method = {RequestMethod.GET})
    @PreAuthorize("@p.check('${changeClassName}:list')")
    public R getByPage(@RequestParam String q, @RequestParam String fields, @PageableDefault(value = 25, sort = {"${pkChangeColName}"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.queryMultiField(q, fields, page);
    }

    /**
     * Description: 使用${pkChangeColName}来获取实体
     *
     * @param ${pkChangeColName} 主键
     * @return R
     */
    @Override
    @Log("依ID查询${apiAlias}")
    @GetMapping("/{${pkChangeColName}}")
    @ApiOperation(value = "依据${pkChangeColName}来获取对应的记录", notes = "依据${pkChangeColName}来获取对应的记录")
    @PreAuthorize("@p.check('${changeClassName}:list')")
    public R getById(@PathVariable("${pkChangeColName}") ${pkColumnType} ${pkChangeColName}) {
        return super.getById(${pkChangeColName});
    }

    /**
     * Description: 保存实体
     *
     * @param entity 要保存的对象
     * @return R
     */
    @Override
    @Log("新增${apiAlias}")
    @PostMapping
    @ApiOperation(value = "保存记录", notes = "保存来自json格式对象")
    @PreAuthorize("@p.check('${changeClassName}:add')")
    public R save(@RequestBody @ApiParam(name = "entity", value = "Json格式", required = true) ${className} entity) {
        return super.save(entity);
    }

    /**
     * Description: 依据${pkChangeColName}更新数据库,不用空值更新已存在的值
     *
     * @param entity 将更新的对象
     * @return R
     */
    @Override
    @Log("依ID修改${apiAlias}")
    @PutMapping
    @ApiOperation(value = "依据${pkChangeColName}来更新对应的记录", notes = "依据${pkChangeColName}来更新对应的记录,属性值为空则不更新数据表中已有的数据")
    @PreAuthorize("@p.check('${changeClassName}:edit')")
    public R updateById(@RequestBody @ApiParam(name = "entity", value = "Json格式", required = true) ${className} entity) {
        return super.updateById(entity);
    }

    /**
     * Description: 依据${pkChangeColName}来删除实体
     *
     * @param ${pkChangeColName} 主键
     * @return R
     */
    @Override
    @Log("依ID删除${apiAlias}")
    @DeleteMapping("/{${pkChangeColName}}")
    @ApiOperation(value = "依据${pkChangeColName}来删除对应的记录", notes = "依据${pkChangeColName}来删除对应的记录")
    @PreAuthorize("@p.check('${changeClassName}:del')")
    public R deleteById(@PathVariable("${pkChangeColName}") ${pkColumnType} ${pkChangeColName}) {
        return super.deleteById(${pkChangeColName});
    }

    /**
    * Description: 依据${pkChangeColName}集合来删除实体
    *
    * @param ${pkChangeColName}s 主键集合
    * @return R
    */
    @Override
    @Log("依ID集合删除${apiAlias}")
    @DeleteMapping
    @ApiOperation(value = "依据${pkChangeColName}集合来删除对应的记录", notes = "依据${pkChangeColName}集合来删除对应的记录")
    @PreAuthorize("@p.check('${changeClassName}:del')")
    public R deleteByIds(@RequestBody Set<${pkColumnType}> ${pkChangeColName}s) {
        return super.deleteByIds(${pkChangeColName}s);
    }

    /**
    * 不分页,导出数据为excel文件
    * @param response 请求响应对象
    * @param criteria 查询条件对象
    * @throws IOException
    */
    @Log("导出${apiAlias}数据")
    @ApiOperation("导出${apiAlias}数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('${changeClassName}:list')")
    public void download(HttpServletResponse response, ${className}QueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }
}
