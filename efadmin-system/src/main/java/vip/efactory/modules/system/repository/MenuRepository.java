package vip.efactory.modules.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.system.entity.Menu;

import java.util.LinkedHashSet;
import java.util.List;

public interface MenuRepository extends BaseRepository<Menu, Long>, JpaSpecificationExecutor {

    /**
     * findByName
     *
     * @param name
     * @return
     */
    Menu findByName(String name);

    /**
     * findByPid
     *
     * @param pid
     * @return
     */
    List<Menu> findByPid(long pid);

    LinkedHashSet<Menu> findByRoles_IdOrderBySortAsc(Long id);
}
