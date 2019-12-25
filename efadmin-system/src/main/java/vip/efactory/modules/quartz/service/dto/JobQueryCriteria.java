package vip.efactory.modules.quartz.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

@Data
public class JobQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String jobName;

    @Query
    private Boolean isSuccess;
}
