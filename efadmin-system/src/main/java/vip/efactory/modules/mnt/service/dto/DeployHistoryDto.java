package vip.efactory.modules.mnt.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class DeployHistoryDto implements Serializable {

	/**
	 * 编号
	 */
    private String id;

	/**
	 * 应用名称
	 */
    private String appName;

	/**
	 * 部署IP
	 */
    private String ip;

	/**
	 * 部署时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime deployDate;

	/**
	 * 部署人员
	 */
	private String deployUser;

	/**
	 * 部署编号
	 */
	private Long deployId;
}
