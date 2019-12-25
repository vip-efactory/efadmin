package vip.efactory.modules.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.system.entity.Dept;

import java.util.List;
import java.util.Set;

public interface DeptRepository extends BaseRepository<Dept, Long>, JpaSpecificationExecutor {

    /**
     * findByPid
     *
     * @param id
     * @return
     */
    List<Dept> findByPid(Long id);

    @Query(value = "select name from sys_dept where id = ?1", nativeQuery = true)
    String findNameById(Long id);

    Set<Dept> findByRoles_Id(Long id);
}
