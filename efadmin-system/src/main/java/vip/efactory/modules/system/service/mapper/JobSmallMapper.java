package vip.efactory.modules.system.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vip.efactory.mapper.EntityMapper;
import vip.efactory.modules.system.entity.Job;
import vip.efactory.modules.system.service.dto.JobSmallDTO;


@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobSmallMapper extends EntityMapper<JobSmallDTO, Job> {

}
