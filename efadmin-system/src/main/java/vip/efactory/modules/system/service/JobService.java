package vip.efactory.modules.system.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.entity.Job;
import vip.efactory.modules.system.service.dto.JobDTO;
import vip.efactory.modules.system.service.dto.JobQueryCriteria;

@CacheConfig(cacheNames = "job")
public interface JobService extends IBaseService<Job, Long> {

    /**
     * findJobDTOById
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    JobDTO findJobDTOById(Long id);

    /**
     * create
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    JobDTO create(Job resources);

    /**
     * update
     *
     * @param resources
     */
    @CacheEvict(allEntries = true)
    Job update(Job resources);

    /**
     * delete
     *
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * queryAll
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAll(JobQueryCriteria criteria, Pageable pageable);
}
