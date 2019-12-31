package ${package}.service.mapper;

import vip.efactory.mapper.BaseMapper;
import ${package}.domain.${className};
import ${package}.service.dto.${className}Dto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* ${apiAlias} Mapper层
* @author ${author}
* @date ${date}
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ${className}Mapper extends BaseMapper<${className}Dto, ${className}> {

}