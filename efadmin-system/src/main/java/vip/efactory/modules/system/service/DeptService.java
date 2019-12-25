package vip.efactory.modules.system.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.entity.Dept;
import vip.efactory.modules.system.service.dto.DeptDTO;
import vip.efactory.modules.system.service.dto.DeptQueryCriteria;

import java.util.List;
import java.util.Set;

@CacheConfig(cacheNames = "dept")
public interface DeptService extends IBaseService<Dept, Long> {

    /**
     * queryAll
     *
     * @param criteria
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<DeptDTO> queryAll(DeptQueryCriteria criteria);

    /**
     * findJobDTOById
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DeptDTO findDeptDTOById(Long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DeptDTO create(Dept resources);

    /**
     * update
     *
     * @param resources
     */
    @CacheEvict(allEntries = true)
    Dept update(Dept resources);

    /**
     * delete
     *
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * buildTree
     *
     * @param deptDTOS
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object buildTree(List<DeptDTO> deptDTOS);

    /**
     * findByPid
     *
     * @param pid
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<Dept> findByPid(long pid);

    Set<Dept> findByRoleIds(Long id);
}
