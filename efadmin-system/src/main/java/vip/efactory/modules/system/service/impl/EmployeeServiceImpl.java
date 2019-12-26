package vip.efactory.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.EntityExistException;
import vip.efactory.modules.system.entity.Employee;
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
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, Long, EmployeeRepository> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Object queryAll(EmployeeQueryCriteria criteria, Pageable pageable) {
        Page<Employee> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(employeeMapper::toDto));
    }

    @Override
    public Object queryAll(EmployeeQueryCriteria criteria) {
        return employeeMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public EmployeeDto findDTOById(Long id) {
        Optional<Employee> employee = br.findById(id);
        ValidationUtil.isNull(employee, "Employee", "id", id);
        return employeeMapper.toDto(employee.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeDto create(Employee resources) {
        if (br.findByCode(resources.getCode()) != null) {
            throw new EntityExistException(Employee.class, "code", resources.getCode());
        }
        if (br.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(Employee.class, "email", resources.getEmail());
        }
        if (br.findByPhone(resources.getPhone()) != null) {
            throw new EntityExistException(Employee.class, "phone", resources.getPhone());
        }
        return employeeMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Employee update(Employee resources) {
        Optional<Employee> optionalEmployee = br.findById(resources.getId());
        ValidationUtil.isNull(optionalEmployee, "Employee", "id", resources.getId());
        Employee employee = optionalEmployee.get();
        Employee employee1 = null;
        employee1 = br.findByCode(resources.getCode());
        if (employee1 != null && !employee1.getId().equals(employee.getId())) {
            throw new EntityExistException(Employee.class, "code", resources.getCode());
        }
        employee1 = br.findByEmail(resources.getEmail());
        if (employee1 != null && !employee1.getId().equals(employee.getId())) {
            throw new EntityExistException(Employee.class, "email", resources.getEmail());
        }
        employee1 = br.findByPhone(resources.getPhone());
        if (employee1 != null && !employee1.getId().equals(employee.getId())) {
            throw new EntityExistException(Employee.class, "phone", resources.getPhone());
        }
        employee.copy(resources);
        br.save(employee);
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        br.deleteById(id);
    }
}
