package vip.efactory.modules.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.system.entity.Permission;

import java.util.List;

public interface PermissionRepository extends BaseRepository<Permission, Long>, JpaSpecificationExecutor {

    /**
     * findByName
     *
     * @param name
     * @return
     */
    Permission findByName(String name);

    /**
     * findByPid
     *
     * @param pid
     * @return
     */
    List<Permission> findByPid(long pid);
}
