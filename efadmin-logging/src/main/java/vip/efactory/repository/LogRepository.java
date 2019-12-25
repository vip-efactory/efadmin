package vip.efactory.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.entity.Log;

/**
 * Description: 日志持久化层
 *
 * @author dbdu
 * @date 19-7-10 上午10:44
 */
@Repository
public interface LogRepository extends BaseRepository<Log, Long> {

    /**
     * 获取一个时间段的IP记录
     *
     * @param date1
     * @param date2
     * @return
     */
    @Query(value = "select count(*) FROM (select request_ip FROM sys_log where create_time between ?1 and ?2 GROUP BY request_ip) as s", nativeQuery = true)
    Long findIp(String date1, String date2);

    /**
     * findExceptionById
     *
     * @param id
     * @return
     */
    @Query(value = "select exception_detail FROM sys_log where id = ?1", nativeQuery = true)
    String findExceptionById(Long id);
}
