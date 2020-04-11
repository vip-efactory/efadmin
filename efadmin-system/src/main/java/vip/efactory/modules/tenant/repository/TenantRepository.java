package vip.efactory.modules.tenant.repository;

import vip.efactory.modules.tenant.domain.Tenant;
import vip.efactory.ejpa.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* 系统租户 持久化层
* @author vip-efactory
* @date 2020-04-11
*/
public interface TenantRepository extends BaseRepository<Tenant, Long>, JpaSpecificationExecutor<Tenant> {
    
}