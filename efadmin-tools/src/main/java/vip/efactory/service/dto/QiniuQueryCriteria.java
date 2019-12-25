package vip.efactory.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

@Data
public class QiniuQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String key;
}
