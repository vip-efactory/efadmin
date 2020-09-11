package vip.efactory.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日志查询类
 */
@Data
public class LogQueryCriteria {

    @Query(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    @Query
    private String logType;

    @Query(type = Query.Type.BETWEEN)
    private List<LocalDateTime> createTime;
}
