package vip.efactory.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vip.efactory.mapper.EntityMapper;
import vip.efactory.modules.system.entity.Dept;
import vip.efactory.modules.system.service.dto.DeptDTO;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends EntityMapper<DeptDTO, Dept> {

}
