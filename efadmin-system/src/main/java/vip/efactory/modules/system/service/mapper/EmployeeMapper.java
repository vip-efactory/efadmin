package vip.efactory.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vip.efactory.mapper.EntityMapper;
import vip.efactory.modules.system.entity.Employee;
import vip.efactory.modules.system.service.dto.EmployeeDTO;

/**
 * @author dbdu
 * @date 2019-07-21
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

}
