package vip.efactory.modules.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.system.domain.Dept;

import java.util.List;
import java.util.Set;

public interface DeptRepository extends BaseRepository<Dept, Long>, JpaSpecificationExecutor {

    /**
     * 根据 PID 查询
     * @param id pid
     * @return /
     */
    List<Dept> findByPid(Long id);

    /**
     * 根据ID查询名称
     * @param id ID
     * @return /
     */
    @Query(value = "select name from sys_dept where id = ?1",nativeQuery = true)
    String findNameById(Long id);

    /**
     * 根据角色ID 查询
     * @param id 角色ID
     * @return /
     */
    Set<Dept> findByRoles_Id(Long id);
}