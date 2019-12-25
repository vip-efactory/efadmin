package ${package}.rest;

import vip.efactory.aop.log.Log;
import ${package}.entity.${className};
import ${package}.service.${className}Service;
import ${package}.service.dto.${className}QueryCriteria;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* ${tableRemark}
* @author ${author}
*/
@Api(tags = "${className}管理")
@RestController
// @RequestMapping("api")
@RequestMapping("${changeClassName}")
public class ${className}Controller extends BaseController<${className}, ${className}Service> {
/**
    @Log("查询${className}")
    @ApiOperation(value = "查询${className}")
    @GetMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_SELECT')")
    public ResponseEntity get${className}s(${className}QueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(entityService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增${className}")
    @ApiOperation(value = "新增${className}")
    @PostMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ${className} resources){
        return new ResponseEntity(entityService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改${className}")
    @ApiOperation(value = "修改${className}")
    @PutMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ${className} resources){
        entityService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除${className}")
    @ApiOperation(value = "删除${className}")
    @DeleteMapping(value = "/${changeClassName}/{${pkChangeColName}}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_DELETE')")
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
     * @author ${author}
     */
    @Log("分页查询${className}")
    @ApiOperation(value = "获取分页数据", notes = "默认每页25条记录,${pkChangeColName}字段降序")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_SELECT')")
    public R getByPage(@PageableDefault(value = 25, sort = {"${pkChangeColName}"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.getByPage(page);
    }

    /**
     * Description: 多条件高级查询;默认的分页与排序,默认${pkChangeColName}降序
     *
     * @param baseEntity 含有高级搜索条件的基础对象
     * @param page 框架默认的分页对象
     * @return R
     * @author ${author}
     */
    @Log("高级查询${className}")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,${pkChangeColName}字段降序")
    @RequestMapping(value = "/advanced/query", method = {RequestMethod.POST})
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_SELECT')")
    public R advancedQuery(@RequestBody BaseEntity baseEntity, @PageableDefault(value = 25, sort = {"${pkChangeColName}"}, direction = Sort.Direction.DESC) Pageable page) {
        ${className} entity = new ${className}();
        BeanUtils.copyProperties(baseEntity, entity);
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
     * @author ${author}
     */
    @Log("多字段模糊查询${className}")
    @ApiOperation(value = "多字段模糊查询,例如:q=abc&fields=name,address,desc", notes = "多个字段模糊匹配")
    @RequestMapping(value = "/fuzzy", method = {RequestMethod.GET})
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_SELECT')")
    public R getByPage(@RequestParam String q, @RequestParam String fields, @PageableDefault(value = 25, sort = {"${pkChangeColName}"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.queryMutiField(q, fields, page);
    }


    /**
     * Description: 使用${pkChangeColName}来获取实体
     *
     * @param ${pkChangeColName} 主键
     * @return R
     * @author ${author}
     */
    @Log("依ID查询${className}")
    @GetMapping("/{${pkChangeColName}}")
    @ApiOperation(value = "依据${pkChangeColName}来获取对应的记录", notes = "依据${pkChangeColName}来获取对应的记录")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_SELECT')")
    public R getById(@PathVariable("${pkChangeColName}") ${pkColumnType} ${pkChangeColName}) {
        return super.getById(${pkChangeColName});
    }


    /**
     * Description: 保存实体
     *
     * @param entity 要保存的对象
     * @return R
     * @author ${author}
     */
    @Log("新增${className}")
    @PostMapping
    @ApiOperation(value = "保存记录", notes = "保存来自json格式对象")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_CREATE')")
    public R save(@RequestBody @ApiParam(name = "entity", value = "Json格式", required = true) ${className} entity) {
        return super.save(entity);
    }

    /**
     * Description: 依据${pkChangeColName}更新数据库,不用空值更新已存在的值
     *
     * @param entity 将更新的对象
     * @return R
     * @author ${author}
     */
    @Log("依ID修改${className}")
    @PutMapping
    @ApiOperation(value = "依据${pkChangeColName}来更新对应的记录", notes = "依据${pkChangeColName}来更新对应的记录,属性值为空则不更新数据表中已有的数据")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_EDIT')")
    public R updateById(@RequestBody @ApiParam(name = "entity", value = "Json格式", required = true) ${className} entity) {
        return super.updateById(entity);
    }

    /**
     * Description: 依据${pkChangeColName}来删除实体
     *
     * @param ${pkChangeColName} 主键
     * @return R
     * @author ${author}
     */
    @Log("依ID删除${className}")
    @DeleteMapping("/{${pkChangeColName}}")
    @ApiOperation(value = "依据${pkChangeColName}来删除对应的记录", notes = "依据${pkChangeColName}来删除对应的记录")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_DELETE')")
    public R deleteById(@PathVariable("${pkChangeColName}") ${pkColumnType} ${pkChangeColName}) {
        return super.deleteById(${pkChangeColName});
    }

}
