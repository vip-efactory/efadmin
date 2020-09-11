package vip.efactory.modules.quartz.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String jobName;

    @Query
    private Boolean isSuccess;

    @Query(type = Query.Type.BETWEEN)
    private List<LocalDateTime> createTime;
}
