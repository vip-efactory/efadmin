package vip.efactory.modules.mnt.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.mnt.domain.Deploy;

public interface DeployRepository extends BaseRepository<Deploy, Long>, JpaSpecificationExecutor<Deploy> {
}
