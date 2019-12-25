package vip.efactory.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.entity.Log;
import vip.efactory.service.dto.LogQueryCriteria;

public interface LogService extends IBaseService<Log, Long> {

    /**
     * queryAll
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAll(LogQueryCriteria criteria, Pageable pageable);

    /**
     * queryAllByUser
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAllByUser(LogQueryCriteria criteria, Pageable pageable);

    /**
     * 新增日志
     *
     * @param username
     * @param ip
     * @param joinPoint
     * @param log
     */
    @Async
    void save(String username, String ip, ProceedingJoinPoint joinPoint, Log log);

    /**
     * 查询异常详情
     *
     * @param id
     * @return
     */
    Object findByErrDetail(Long id);
}
