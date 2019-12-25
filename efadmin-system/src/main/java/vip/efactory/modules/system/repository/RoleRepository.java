package vip.efactory.modules.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.system.entity.Role;

import java.util.Set;

public interface RoleRepository extends BaseRepository<Role, Long>, JpaSpecificationExecutor {

    /**
     * findByName
     *
     * @param name
     * @return
     */
    Role findByName(String name);

    Set<Role> findByUsers_Id(Long id);

    Set<Role> findByMenus_Id(Long id);
}
