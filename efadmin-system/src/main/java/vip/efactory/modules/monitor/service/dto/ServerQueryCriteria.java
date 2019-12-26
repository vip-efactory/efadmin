package vip.efactory.modules.monitor.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

/**
* @author Zhang houying
* @date 2019-11-03
*/
@Data
public class ServerQueryCriteria{

    @Query(blurry = "name,address")
    private String blurry;
}