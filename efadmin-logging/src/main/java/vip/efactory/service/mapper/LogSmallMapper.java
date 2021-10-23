package vip.efactory.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vip.efactory.base.BaseMapper;
import vip.efactory.domain.SysLog;
import vip.efactory.service.dto.LogSmallDTO;

/**
 * @author dusuanyun
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogSmallMapper extends BaseMapper<LogSmallDTO, SysLog> {

}
