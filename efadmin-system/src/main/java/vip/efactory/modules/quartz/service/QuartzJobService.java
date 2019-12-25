package vip.efactory.modules.quartz.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.quartz.entity.QuartzJob;
import vip.efactory.modules.quartz.service.dto.JobQueryCriteria;

import java.util.Optional;

@CacheConfig(cacheNames = "quartzJob")
public interface QuartzJobService extends IBaseService<QuartzJob, Long> {

    /**
     * queryAll quartzJob
     *
     * @param criteria
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(JobQueryCriteria criteria, Pageable pageable);

    /**
     * queryAll quartzLog
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAllLog(JobQueryCriteria criteria, Pageable pageable);

    /**
     * create
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    QuartzJob create(QuartzJob resources);

    /**
     * update
     *
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    QuartzJob update(QuartzJob resources);

    /**
     * del
     *
     * @param quartzJob
     */
    @CacheEvict(allEntries = true)
    void delete(QuartzJob quartzJob);

    /**
     * findJobDTOById
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    Optional<QuartzJob> findById(Long id);

    /**
     * 更改定时任务状态
     *
     * @param quartzJob
     */
    @CacheEvict(allEntries = true)
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 立即执行定时任务
     *
     * @param quartzJob
     */
    void execution(QuartzJob quartzJob);
}
