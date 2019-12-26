package vip.efactory.modules.mnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.modules.mnt.domain.DeployHistory;

/**
* @author zhanghouying
* @date 2019-08-24
*/
public interface DeployHistoryRepository extends JpaRepository<DeployHistory, String>, JpaSpecificationExecutor<DeployHistory> {
}
