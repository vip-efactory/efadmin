package vip.efactory.modules.system.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.system.entity.DictDetail;

public interface DictDetailRepository extends BaseRepository<DictDetail, Long>, JpaSpecificationExecutor {
}
