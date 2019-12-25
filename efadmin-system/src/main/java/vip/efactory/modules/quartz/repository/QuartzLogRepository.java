package vip.efactory.modules.quartz.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.quartz.entity.QuartzLog;

public interface QuartzLogRepository extends BaseRepository<QuartzLog, Long>, JpaSpecificationExecutor {

}
