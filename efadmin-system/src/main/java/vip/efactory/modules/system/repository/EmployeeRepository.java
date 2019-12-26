package vip.efactory.modules.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.system.entity.Employee;

/**
 * @author dbdu
 * @date 2019-07-21
 */
public interface EmployeeRepository extends BaseRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    /**
     * findByCode
     *
     * @param code
     * @return
     */
    Employee findByCode(String code);

    /**
     * findByEmail
     *
     * @param email
     * @return
     */
    Employee findByEmail(String email);

    /**
     * findByPhone
     *
     * @param phone
     * @return
     */
    Employee findByPhone(String phone);
}
