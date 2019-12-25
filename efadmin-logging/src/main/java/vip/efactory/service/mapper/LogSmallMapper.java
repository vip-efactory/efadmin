package vip.efactory.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vip.efactory.entity.Log;
import vip.efactory.mapper.EntityMapper;
import vip.efactory.service.dto.LogSmallDTO;

@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogSmallMapper extends EntityMapper<LogSmallDTO, Log> {

}
