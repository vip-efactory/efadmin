package vip.efactory.modules.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.system.domain.Job;

public interface JobRepository extends BaseRepository<Job, Long>, JpaSpecificationExecutor {
}
