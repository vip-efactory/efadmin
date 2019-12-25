package vip.efactory.modules.system.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.entity.Menu;
import vip.efactory.modules.system.entity.Role;
import vip.efactory.modules.system.service.dto.CommonQueryCriteria;
import vip.efactory.modules.system.service.dto.RoleDTO;
import vip.efactory.modules.system.service.dto.RoleSmallDTO;

import java.util.List;
import java.util.Set;

@CacheConfig(cacheNames = "role")
public interface RoleService extends IBaseService<Role, Long> {

    /**
     * get
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    RoleDTO findRoleDTOById(long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    RoleDTO create(Role resources);

    /**
     * update
     *
     * @param resources
     */
    @CacheEvict(allEntries = true)
    Role update(Role resources);

    /**
     * delete
     *
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * key的名称如有修改，请同步修改 UserServiceImpl 中的 update 方法
     * findByUsers_Id
     *
     * @param id
     * @return
     */
    @Cacheable(key = "'findByUsers_Id:' + #p0")
    List<RoleSmallDTO> findByUsers_Id(Long id);

    @Cacheable(keyGenerator = "keyGenerator")
    Integer findByRoles(Set<Role> roles);

    /**
     * updatePermission
     *
     * @param resources
     * @param roleDTO
     */
    @CacheEvict(allEntries = true)
    void updatePermission(Role resources, RoleDTO roleDTO);

    /**
     * updateMenu
     *
     * @param resources
     * @param roleDTO
     */
    @CacheEvict(allEntries = true)
    void updateMenu(Role resources, RoleDTO roleDTO);

    @CacheEvict(allEntries = true)
    void untiedMenu(Menu menu);

    /**
     * queryAll
     *
     * @param pageable
     * @return
     */
    Object queryAll(Pageable pageable);

    /**
     * queryAll
     *
     * @param pageable
     * @param criteria
     * @return
     */
    Object queryAll(CommonQueryCriteria criteria, Pageable pageable);
}
