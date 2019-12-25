package vip.efactory.modules.system.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.entity.Dict;
import vip.efactory.modules.system.service.dto.DictDTO;

@CacheConfig(cacheNames = "dict")
public interface DictService extends IBaseService<Dict, Long> {

    /**
     * 查询
     *
     * @param dict
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(DictDTO dict, Pageable pageable);

    /**
     * findJobDTOById
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DictDTO findDictDTOById(Long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DictDTO create(Dict resources);

    /**
     * update
     *
     * @param resources
     */
    @CacheEvict(allEntries = true)
    Dict update(Dict resources);

    /**
     * delete
     *
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}
