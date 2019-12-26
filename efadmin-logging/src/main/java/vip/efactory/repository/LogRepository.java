package vip.efactory.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.domain.SysLog;

/**
 * Description: 日志持久化层
 *
 * @author dbdu
 * @date 19-7-10 上午10:44
 */
@Repository
public interface LogRepository extends BaseRepository<SysLog, Long> {

    /**
     * 获取一个时间段的IP记录
     * @param date1 startTime
     * @param date2 entTime
     * @return IP数目
     */
    @Query(value = "select count(*) FROM (select request_ip FROM sys_log where create_time between ?1 and ?2 GROUP BY request_ip) as s",nativeQuery = true)
    Long findIp(String date1, String date2);

    /**
     * 根据日志类型删除信息
     * @param logType 日志类型
     */
    @Query(nativeQuery = true,value = "delete from sys_log where log_type = ?1")
    @Modifying
    void deleteByLogType(String logType);
}
