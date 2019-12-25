package vip.efactory.modules.system.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

import java.io.Serializable;

@Data
public class DictDTO implements Serializable {

    private Long id;

    /**
     * 字典名称
     */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    /**
     * 描述
     */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;
}
