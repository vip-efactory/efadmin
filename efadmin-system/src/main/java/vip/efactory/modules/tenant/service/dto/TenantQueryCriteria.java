package vip.efactory.modules.tenant.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

/**
* 系统租户 查询条件
* @author vip-efactory
* @date 2020-04-11
*/
@Data
public class TenantQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String tenantName;
}
