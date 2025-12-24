package vip.efactory.modules.system.service;

import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.domain.Employee;
import vip.efactory.modules.system.service.dto.EmployeeDto;
import vip.efactory.modules.system.service.dto.EmployeeQueryCriteria;

import java.util.Set;

/**
 * @author dbdu
 * @date 2019-07-21
 */
public interface EmployeeService extends IBaseService<Employee, Long> {

    /**
     * 分页查询（带条件）
     */
    Object queryAll(EmployeeQueryCriteria criteria, Pageable pageable);

    /**
     * 不分页查询（带条件）
     */
    Object queryAll(EmployeeQueryCriteria criteria);

    /**
     * 单个查询（返回DTO）
     */
    EmployeeDto findDTOById(Long id);

    /**
     * 新增
     */
    EmployeeDto create(Employee resources);

    /**
     * 修改
     */
    Employee update(Employee resources);

    /**
     * 单个删除
     */
    void delete(Long id);

    /**
     * 批量删除（默认实现）
     */
    default void deleteByIds(Set<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }
}