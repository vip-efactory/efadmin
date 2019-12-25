package vip.efactory.modules.system.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

import java.util.Set;

@Data
public class DeptQueryCriteria {

    @Query(type = Query.Type.IN, propName = "id")
    private Set<Long> ids;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query
    private Long pid;
}
