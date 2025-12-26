package vip.efactory.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import vip.efactory.base.BaseMapper;
import vip.efactory.modules.system.domain.Employee;
import vip.efactory.modules.system.service.dto.EmployeeDto;
import java.util.List;

/**
 * @author dbdu
 * @date 2019-07-21
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends BaseMapper<EmployeeDto, Employee> {

    @Override
    @Mappings({
            @Mapping(source = "dept.name", target = "deptName"),
            @Mapping(source = "job.name",  target = "jobName"),
            @Mapping(source = "dept.id",   target = "deptId"),
            @Mapping(source = "job.id",    target = "jobId")
    })
    EmployeeDto toDto(vip.efactory.modules.system.domain.Employee entity);

    @Override
    List<EmployeeDto> toDto(List<vip.efactory.modules.system.domain.Employee> entities);

}
