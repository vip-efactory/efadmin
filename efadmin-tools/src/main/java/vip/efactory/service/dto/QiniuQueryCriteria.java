package vip.efactory.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class QiniuQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String key;

    @Query(type = Query.Type.BETWEEN)
    private List<LocalDateTime> createTime;
}
