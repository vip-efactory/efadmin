package ${package}.rest;

import vip.efactory.aop.log.Log;
import ${package}.domain.${className};
import ${package}.service.I${className}Service;
import ${package}.service.dto.${className}QueryCriteria;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.base.entity.BaseSearchEntity;
import vip.efactory.ejpa.utils.R;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* ${apiAlias} 控制器层
* @author ${author}
* @date ${date}
*/
@Api(tags = "${apiAlias}管理")
@RestController
// @RequestMapping("api")
@RequestMapping("api/${changeClassName}")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class ${className}Controller extends BaseController<${className}, I${className}Service, ${pkColumnType}> {
/**
    @Log("查询${apiAlias}")
    @ApiOperation(value = "查询${className}")
    @GetMapping
    @PreAuthorize("@p.check('${changeClassName}:list')")
    public ResponseEntity get${className}s(${className}QueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(entityService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增${apiAlias}")
    @ApiOperation(value = "新增${className}")
    @PostMapping
    @PreAuthorize("@p.check('${changeClassName}:add')")
    public ResponseEntity create(@Validated @RequestBody ${className} resources){
        return new ResponseEntity(entityService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改${apiAlias}")
    @ApiOperation(value = "修改${className}")
    @PutMapping
    @PreAuthorize("@p.check('${changeClassName}:edit')")
    public ResponseEntity update(@Validated @RequestBody ${className} resources){
        entityService.edit(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除${apiAlias}")
    @ApiOperation(value = "删除${className}")
    @DeleteMapping(value = "/{${pkChangeColName}}")
    @PreAuthorize("@p.check('${changeClassName}:del')")
    public ResponseEntity delete(@PathVariable ${pkColumnType} ${pkChangeColName}){
        entityService.delete(${pkChangeColName});
        return new ResponseEntity(HttpStatus.OK);
    }
*/

    /**
     * Description: 默认的分页与排序,默认${pkChangeColName}降序
     *
     * @param page 框架默认的分页对象
     * @return R
     */
    @Log("分页查询${apiAlias}")
    @ApiOperation(value = "获取分页数据", notes = "默认每页25条记录,${pkChangeColName}字段降序")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    @PreAuthorize("@p.check('${changeClassName}:list')")
    public R getByPage(@PageableDefault(value = 25, sort = {"${pkChangeColName}"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.getByPage(page);
    }

    /**
     * Description: 多条件高级查询;默认的分页与排序,默认${pkChangeColName}降序
     *
     * @param baseEntity 含有高级搜索条件的基础对象
     * @param page 框架默认的分页对象
     * @return R
     */
    @Log("高级查询${apiAlias}")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,${pkChangeColName}字段降序")
    @RequestMapping(value = "/advanced/query", method = {RequestMethod.POST})
    @PreAuthorize("@p.check('${changeClassName}:list')")
    public R advancedQuery(@RequestBody BaseSearchEntity searchEntity, @PageableDefault(value = 25, sort = {"${pkChangeColName}"}, direction = Sort.Direction.DESC) Pageable page) {
        ${className} entity = new ${className}();
        BeanUtils.copyProperties(searchEntity, entity);
        return super.advancedQueryByPage(page, entity);
    }

    /**
     * Description: 多字段模糊查询,默认${pkChangeColName}降序,例如:
     * http://localhost:8080/${changeClassName}/fuzzy?fields=name,version&q=BB
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
        return super.queryMutiField(q, fields, page);
    }


    /**
     * Description: 使用${pkChangeColName}来获取实体
     *
     * @param ${pkChangeColName} 主键
     * @return R
     */
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
    @Log("依ID删除${apiAlias}")
    @DeleteMapping("/{${pkChangeColName}}")
    @ApiOperation(value = "依据${pkChangeColName}来删除对应的记录", notes = "依据${pkChangeColName}来删除对应的记录")
    @PreAuthorize("@p.check('${changeClassName}:del')")
    public R deleteById(@PathVariable("${pkChangeColName}") ${pkColumnType} ${pkChangeColName}) {
        return super.deleteById(${pkChangeColName});
    }

}
