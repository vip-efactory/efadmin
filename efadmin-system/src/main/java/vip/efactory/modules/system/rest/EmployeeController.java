package vip.efactory.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.modules.system.entity.Employee;
import vip.efactory.modules.system.service.EmployeeService;
import vip.efactory.modules.system.service.dto.EmployeeQueryCriteria;

import javax.validation.Valid;


/**
 * @author dbdu
 * @date 2019-07-21
 */
@Api(tags = "Employee管理")
@RestController
@RequestMapping("api")
public class EmployeeController extends BaseController<Employee, EmployeeService> {

    @Log("查询Employee")
    @ApiOperation(value = "查询Employee")
    @GetMapping(value = "/employee")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_SELECT')")
    public ResponseEntity getEmployees(EmployeeQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(entityService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @Log("新增Employee")
    @ApiOperation(value = "新增Employee")
    @PostMapping(value = "/employee")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Employee resources) {
        return new ResponseEntity(entityService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改Employee")
    @ApiOperation(value = "修改Employee")
    @PutMapping(value = "/employee")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_EDIT')")
    public ResponseEntity update(@Validated @RequestBody Employee resources) {
        entityService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除Employee")
    @ApiOperation(value = "删除Employee")
    @DeleteMapping(value = "/employee/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE_ALL','EMPLOYEE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        entityService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
