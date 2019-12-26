package vip.efactory.modules.monitor.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.monitor.domain.Visits;

import java.util.List;

@Repository
public interface VisitsRepository extends BaseRepository<Visits, Long> {

    /**
     * findByDate
     * @param date 日期
     * @return Visits
     */
    Visits findByDate(String date);

    /**
     * 获得一个时间段的记录
     * @param date1 日期1
     * @param date2 日期2
     * @return List
     */
    @Query(value = "select * FROM sys_visits where " + "create_time between ?1 and ?2", nativeQuery = true)
    List<Visits> findAllVisits(String date1, String date2);
}
