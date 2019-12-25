package vip.efactory.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.entity.GenConfig;


@CacheConfig(cacheNames = "genConfig")
public interface GenConfigService extends IBaseService<GenConfig, Long> {

    /**
     * find
     *
     * @return
     */
    @Cacheable(key = "'1'")
    GenConfig find();

    /**
     * update
     *
     * @param genConfig
     * @return
     */
    @CacheEvict(allEntries = true)
    GenConfig update(GenConfig genConfig);
}
