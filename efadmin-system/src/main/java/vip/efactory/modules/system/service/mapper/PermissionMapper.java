package vip.efactory.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vip.efactory.base.BaseMapper;
import vip.efactory.modules.system.entity.Permission;
import vip.efactory.modules.system.service.dto.PermissionDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends BaseMapper<PermissionDTO, Permission> {

}
