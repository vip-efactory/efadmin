package vip.efactory.modules.tenant.service.mapper;

import vip.efactory.base.BaseMapper;
import vip.efactory.modules.tenant.domain.Tenant;
import vip.efactory.modules.tenant.service.dto.TenantDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* 系统租户 Mapper层
* @author vip-efactory
* @date 2020-04-11
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TenantMapper extends BaseMapper<TenantDto, Tenant> {

}