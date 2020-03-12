package vip.efactory.modules.mnt.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.mnt.domain.DeployHistory;

public interface DeployHistoryRepository extends BaseRepository<DeployHistory, String>, JpaSpecificationExecutor<DeployHistory> {
}
