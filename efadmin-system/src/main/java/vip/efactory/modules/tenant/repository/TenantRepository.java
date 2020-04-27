package vip.efactory.modules.tenant.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.modules.tenant.domain.Tenant;

import java.util.List;

/**
 * 系统租户 持久化层
 *
 * @author vip-efactory
 * @date 2020-04-11
 */
public interface TenantRepository extends BaseRepository<Tenant, Long>, JpaSpecificationExecutor<Tenant> {
    // 根据租户状态来查询所有的列表
    List<Tenant> findAllByStatusEquals(Integer enable);

}
