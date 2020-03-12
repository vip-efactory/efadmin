package vip.efactory.modules.mnt.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.mnt.domain.Database;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
public interface DatabaseRepository extends BaseRepository<Database, String>, JpaSpecificationExecutor<Database> {
}
