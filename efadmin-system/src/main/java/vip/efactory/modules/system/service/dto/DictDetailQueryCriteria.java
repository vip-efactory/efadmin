package vip.efactory.modules.system.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

@Data
public class DictDetailQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name", joinName = "dict")
    private String dictName;
}
