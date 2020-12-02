package vip.efactory.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Entity
@Data
@Table(name="sys_mnt_deploy_history")
public class DeployHistory extends BaseEntity<String> implements Serializable {

	/**
	 * 编号
	 */
    @Id
    @Column(name = "id")
    private String id;

	/**
	 * 应用名称
	 */
    @Column(name = "app_name",nullable = false)
    private String appName;

	/**
	 * 部署IP
	 */
    @Column(name = "ip",nullable = false)
    private String ip;

	/**
	 * 部署时间
	 */
    @Column(name = "deploy_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime deployDate;

	/**
	 * 部署人员
	 */
    @Column(name = "deploy_user",nullable = false)
    private String deployUser;

	/**
	 * 部署编号
	 */
	@Column(name = "deploy_id",nullable = false)
	private Long deployId;

    public void copy(DeployHistory source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
