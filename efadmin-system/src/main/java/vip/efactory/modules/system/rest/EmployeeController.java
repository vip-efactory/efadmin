package vip.efactory.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.base.entity.BaseSearchEntity;
import vip.efactory.ejpa.utils.R;
import vip.efactory.modules.system.domain.Employee;
import vip.efactory.modules.system.service.EmployeeService;

import java.util.Set;


/**
 * @author dbdu
 * @date 2019-07-21
 */
@Api(tags = "Employee管理")
@RestController
@RequestMapping("api/employee")
public class EmployeeController extends BaseController<Employee, EmployeeService, Long> {

//    @Log("查询员工信息")
//    @ApiOperation(value = "查询Employee")
//    @GetMapping
//    @PreAuthorize("@p.check('employee:list')")
//    public ResponseEntity getEmployees(EmployeeQueryCriteria criteria, Pageable pageable){
//        return new ResponseEntity(entityService.queryAll(criteria,pageable),HttpStatus.OK);
//    }
//
//    @Log("新增员工信息")
//    @ApiOperation(value = "新增Employee")
//    @PostMapping
//    @PreAuthorize("@p.check('employee:add')")
//    public ResponseEntity create(@Validated @RequestBody Employee resources){
//        return new ResponseEntity(entityService.create(resources),HttpStatus.CREATED);
//    }
//
//    @Log("修改员工信息")
//    @ApiOperation(value = "修改Employee")
//    @PutMapping
//    @PreAuthorize("@p.check('employee:edit')")
//    public ResponseEntity update(@Validated @RequestBody Employee resources){
//        entityService.edit(resources);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//    @Log("删除员工信息")
//    @ApiOperation(value = "删除Employee")
//    @DeleteMapping(value = "/{id}")
//    @PreAuthorize("@p.check('employee:del')")
//    public ResponseEntity delete(@PathVariable Long id){
//        entityService.delete(id);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    // ===============================  以下为使用ejpa的方式================================================== //

    /**
     * Description: 默认的分页与排序
     *
     * @param page 分页参数对象
     * @return R
     */
    @Log("分页查询Employee")
    @ApiOperation(value = "获取分页数据", notes = "默认每页25条记录,id字段降序")
    @GetMapping("/page")
    @PreAuthorize("@p.check('employee:list')")
    public R getByPage(@PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.getByPage(page);
    }

    /**
     * Description: 高级查询
     *
     * @param entity            含有高级查询条件
     * @param page             分页参数对象
     * @return R
     */
    @Log("分页高级查询Employee")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('employee:list')")
    public R advancedQuery(@RequestBody Employee entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    /**
     * Description:多字段模糊查询,例如:
     * http://frms.ddbin.com:8080/carton/fuzzy?fields=name,version&q=BB
     *
     * @param page   分页参数对象
     * @param q      模糊查询的值
     * @param fields 要查询的字段
     * @return R
     */
    @Log("多字段模糊查询Employee")
    @ApiOperation(value = "多字段模糊查询,例如:q=abc&fields=name,address,desc", notes = "多个字段模糊匹配")
    @GetMapping("/fuzzy")
    @PreAuthorize("@p.check('employee:list')")
    public R getByPage(@RequestParam String q, @RequestParam String fields, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.queryMutiField(q, fields, page);
    }


    /**
     * Description:使用id来获取实体
     *
     * @param id 主键
     * @return R
     */
    @Log("使用Id查询Employee")
    @GetMapping("/{id}")
    @ApiOperation(value = "依据Id来获取对应的记录", notes = "依据Id来获取对应的记录")
    @PreAuthorize("@p.check('employee:list')")
    public R getById(@PathVariable("id") Long id) {
        return super.getById(id);
    }


    /**
     * Description:保存实体
     *
     * @param entity 要保存的对象实体
     * @return R
     */
    @Log("新增Employee")
    @PostMapping
    @ApiOperation(value = "新增Employee", notes = "新增Employee实体")
    @PreAuthorize("@p.check('employee:add')")
    public R save(@RequestBody @ApiParam(name = "entity", value = "Json格式", required = true) Employee entity) {
        return super.save(entity);
    }

    /**
     * Description:更新
     *
     * @param entity 更新对象
     * @return R
     */

    @Log("修改Employee")
    @PreAuthorize("@p.check('employee:edit')")
    @PutMapping
    @ApiOperation(value = "依据Id来更新Employee对应的记录", notes = "依据Id来更新对应的记录,属性值为空则不更新数据表中已有的数据")
    public R updateById(@RequestBody @ApiParam(name = "entity", value = "Json格式", required = true) Employee entity) {
        return super.updateById(entity);
    }

    /**
     * Description: 依据ids集合来批量删除实体
     *
     * @param ids 主键
     * @return R
     */
    @Log("使用Ids删除Employee")
    @PreAuthorize("@p.check('employee:del')")
    @DeleteMapping
    @ApiOperation(value = "依据Ids来删除Employee对应的记录", notes = "依据Ids来删除Employee对应的记录")
    public R deleteByIds(@RequestBody Set<Long> ids) {
        return super.deleteByIds(ids);
    }

}
