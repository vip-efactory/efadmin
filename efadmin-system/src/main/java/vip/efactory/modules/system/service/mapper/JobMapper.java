package vip.efactory.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vip.efactory.mapper.EntityMapper;
import vip.efactory.modules.system.entity.Job;
import vip.efactory.modules.system.service.dto.JobDTO;


@Mapper(componentModel = "spring", uses = {DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends EntityMapper<JobDTO, Job> {

    @Mapping(source = "deptSuperiorName", target = "deptSuperiorName")
    JobDTO toDto(Job job, String deptSuperiorName);
}
