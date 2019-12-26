package vip.efactory.modules.system.service;

import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.domain.Employee;
import vip.efactory.modules.system.service.dto.EmployeeDto;
import vip.efactory.modules.system.service.dto.EmployeeQueryCriteria;

//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * @author dbdu
 * @date 2019-07-21
 */
//@CacheConfig(cacheNames = "employee")
public interface EmployeeService extends IBaseService<Employee, Long> {

    /**
     * queryAll 分页
     *
     * @param criteria
     * @param pageable
     * @return
     */
    //@Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(EmployeeQueryCriteria criteria, Pageable pageable);

    /**
     * queryAll 不分页
     *
     * @param criteria
     * @return
     */
    //@Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(EmployeeQueryCriteria criteria);

    /**
     * findById
     *
     * @param id
     * @return
     */
    //@Cacheable(key = "#p0")
    EmployeeDto findDTOById(Long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    //@CacheEvict(allEntries = true)
    EmployeeDto create(Employee resources);

    /**
     * update
     *
     * @param resources
     */
    //@CacheEvict(allEntries = true)
    Employee update(Employee resources);

    /**
     * delete
     *
     * @param id
     */
    //@CacheEvict(allEntries = true)
    void delete(Long id);
}
