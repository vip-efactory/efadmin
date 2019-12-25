package vip.efactory.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

/**
 * 日志查询类
 */
@Data
public class LogQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String username;

    @Query
    private String logType;

    @Query(type = Query.Type.INNER_LIKE)
    private String description;
}
