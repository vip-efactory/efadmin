package vip.efactory.modules.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.EntityExistException;
import vip.efactory.exception.EntityNotFoundException;
import vip.efactory.modules.system.domain.Employee;
import vip.efactory.modules.system.repository.EmployeeRepository;
import vip.efactory.modules.system.service.EmployeeService;
import vip.efactory.modules.system.service.dto.EmployeeDto;
import vip.efactory.modules.system.service.dto.EmployeeQueryCriteria;
import vip.efactory.modules.system.service.mapper.EmployeeMapper;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.Optional;

/**
 * @author dbdu
 * @date 2019-07-21
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "employee")
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, Long, EmployeeRepository> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 分页查询（带条件）
     */
    @Override
    @Cacheable
    public Object queryAll(EmployeeQueryCriteria criteria, Pageable pageable) {
        try {
            Page<Employee> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
            return PageUtil.toPage(page.map(employeeMapper::toDto));
        } catch (Exception e) {
            log.error("分页查询Employee失败", e);
            throw new RuntimeException("分页查询失败", e);
        }
    }

    /**
     * 不分页查询（带条件）
     */
    @Override
    @Cacheable
    public Object queryAll(EmployeeQueryCriteria criteria) {
        try {
            return employeeMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
        } catch (Exception e) {
            log.error("不分页查询Employee失败", e);
            throw new RuntimeException("不分页查询失败", e);
        }
    }

    /**
     * 单个查询（返回DTO）
     */
    @Override
    @Cacheable
    public EmployeeDto findDTOById(Long id) {
        Optional<Employee> employee = br.findById(id);
        ValidationUtil.isNull(employee, "Employee", "id", id);
        return employeeMapper.toDto(employee.get());
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public EmployeeDto create(Employee resources) {
        // 校验编码唯一性
        if (br.findByCode(resources.getCode()) != null) {
            throw new EntityExistException(Employee.class, "code", resources.getCode());
        }
        // 校验邮箱唯一性
        if (br.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(Employee.class, "email", resources.getEmail());
        }
        // 校验手机号唯一性
        if (br.findByPhone(resources.getPhone()) != null) {
            throw new EntityExistException(Employee.class, "phone", resources.getPhone());
        }
        // 保存数据
        Employee save = br.save(resources);
        log.info("新增Employee成功, ID:{}", save.getId());
        return employeeMapper.toDto(save);
    }

    /**
     * 修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public Employee update(Employee resources) {
        // 校验ID是否存在
        Optional<Employee> optionalEmployee = br.findById(resources.getId());
        if (optionalEmployee.isEmpty()) {
            throw new EntityNotFoundException(Employee.class, "id", resources.getId().toString());
        }
        Employee employee = optionalEmployee.get();

        // 校验编码唯一性（排除自身）
        Employee employee1 = br.findByCode(resources.getCode());
        if (employee1 != null && !employee1.getId().equals(employee.getId())) {
            throw new EntityExistException(Employee.class, "code", resources.getCode());
        }
        // 校验邮箱唯一性（排除自身）
        employee1 = br.findByEmail(resources.getEmail());
        if (employee1 != null && !employee1.getId().equals(employee.getId())) {
            throw new EntityExistException(Employee.class, "email", resources.getEmail());
        }
        // 校验手机号唯一性（排除自身）
        employee1 = br.findByPhone(resources.getPhone());
        if (employee1 != null && !employee1.getId().equals(employee.getId())) {
            throw new EntityExistException(Employee.class, "phone", resources.getPhone());
        }

        // 拷贝属性并保存
        employee.copy(resources);
        br.save(employee);
        log.info("修改Employee成功, ID:{}", employee.getId());
        return resources;
    }

    /**
     * 单个删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void delete(Long id) {
        // 校验ID是否存在
        Optional<Employee> employee = br.findById(id);
        if (employee.isEmpty()) {
            throw new EntityNotFoundException(Employee.class, "id", id.toString());
        }
        // 执行删除
        try {
            br.deleteById(id);
            log.info("删除Employee成功, ID:{}", id);
        } catch (Exception e) {
            // 捕获外键关联异常
            if (e.getMessage().contains("foreign key") || e.getMessage().contains("外键")) {
                log.error("删除Employee失败, ID:{}, 原因：关联其他数据", id, e);
                throw new RuntimeException("删除失败：该员工关联了其他数据（如部门/岗位），请先解除关联", e);
            }
            log.error("删除Employee失败, ID:{}", id, e);
            throw new RuntimeException("删除失败", e);
        }
    }
}