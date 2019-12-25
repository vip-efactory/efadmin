package vip.efactory.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vip.efactory.mapper.EntityMapper;
import vip.efactory.modules.system.entity.DictDetail;
import vip.efactory.modules.system.service.dto.DictDetailDTO;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictDetailMapper extends EntityMapper<DictDetailDTO, DictDetail> {

}
