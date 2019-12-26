package vip.efactory.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vip.efactory.base.BaseMapper;
import vip.efactory.modules.system.domain.Employee;
import vip.efactory.modules.system.service.dto.EmployeeDto;

/**
 * @author dbdu
 * @date 2019-07-21
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends BaseMapper<EmployeeDto, Employee> {

}
