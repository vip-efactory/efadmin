package vip.efactory.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vip.efactory.mapper.EntityMapper;
import vip.efactory.modules.system.entity.Role;
import vip.efactory.modules.system.service.dto.RoleSmallDTO;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends EntityMapper<RoleSmallDTO, Role> {

}
