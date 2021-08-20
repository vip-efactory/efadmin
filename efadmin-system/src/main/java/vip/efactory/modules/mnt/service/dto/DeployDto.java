package vip.efactory.modules.mnt.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.hutool.core.collection.CollUtil.isNotEmpty;


/**
* @author zhanghouying
* @date 2019-08-24
*/
@Data
public class DeployDto implements Serializable {

	/**
	 * 部署编号
	 */
    private String id;

	private AppDto app;

	/**
	 * 服务器
	 */
	private Set<ServerDeployDto> deploys;

	private String servers;

	/**
	 * 服务状态
	 */
	private String status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;

	public String getServers() {
		if(isNotEmpty(deploys)){
			return deploys.stream().map(ServerDeployDto::getName).collect(Collectors.joining(","));
		}
		return servers;
	}
}
