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
public class DatabaseDto implements Serializable {

	/**
	 * id
	 */
    private String id;

	/**
	 * 数据库名称
	 */
    private String name;

	/**
	 * 数据库连接地址
	 */
    private String jdbcUrl;

	/**
	 * 数据库密码
	 */
    private String pwd;

	/**
	 * 用户名
	 */
    private String userName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;
}
