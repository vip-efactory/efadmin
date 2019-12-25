package vip.efactory.modules.system.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.entity.DictDetail;
import vip.efactory.modules.system.service.dto.DictDetailDTO;
import vip.efactory.modules.system.service.dto.DictDetailQueryCriteria;

import java.util.Map;

@CacheConfig(cacheNames = "dictDetail")
public interface DictDetailService extends IBaseService<DictDetail, Long> {

    /**
     * findJobDTOById
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DictDetailDTO findDictDeatilDTOById(Long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DictDetailDTO create(DictDetail resources);

    /**
     * update
     *
     * @param resources
     */
    @CacheEvict(allEntries = true)
    DictDetail update(DictDetail resources);

    /**
     * delete
     *
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    @Cacheable(keyGenerator = "keyGenerator")
    Map queryAll(DictDetailQueryCriteria criteria, Pageable pageable);
}
