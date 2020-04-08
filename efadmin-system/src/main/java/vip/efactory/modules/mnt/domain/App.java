package vip.efactory.modules.mnt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Entity
@Data
@Table(name = "sys_mnt_app")
public class App extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;

	/**
	 * 应用编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 应用名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 端口
	 */
	@Column(name = "port")
	private int port;

	/**
	 * 上传目录
	 */
	@Column(name = "upload_path")
	private String uploadPath;

	/**
	 * 部署目录
	 */
	@Column(name = "deploy_path")
	private String deployPath;

	/**
	 * 备份目录
	 */
	@Column(name = "backup_path")
	private String backupPath;

	/**
	 * 启动脚本
	 */
	@Column(name = "start_script")
	private String startScript;

	/**
	 * 部署脚本
	 */
	@Column(name = "deploy_script")
	private String deployScript;

	// @CreationTimestamp
	// private Timestamp createTime;

	public void copy(App source) {
		BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
	}
}
