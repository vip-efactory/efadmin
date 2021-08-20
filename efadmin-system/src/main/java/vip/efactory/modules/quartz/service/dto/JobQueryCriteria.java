package vip.efactory.modules.quartz.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import vip.efactory.annotation.Query;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String jobName;

    @Query
    private Boolean isSuccess;

    @Query(type = Query.Type.BETWEEN)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> createTime;
}
