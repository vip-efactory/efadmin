package vip.efactory.modules.mnt.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class DeployHistoryQueryCriteria{

	/**
	 * 精确
	 */
	@Query(blurry = "appName,ip,deployUser")
	private String blurry;

	@Query
	private Long deployId;

	@Query(type = Query.Type.BETWEEN)
	private List<LocalDateTime> deployDate;
}
