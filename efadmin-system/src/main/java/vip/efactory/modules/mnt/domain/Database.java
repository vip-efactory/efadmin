package vip.efactory.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Entity
@Data
@Table(name="sys_mnt_database")
public class Database extends BaseEntity<String> implements Serializable {

	/**
	 * id
	 */
    @Id
    @Column(name = "id")
    private String id;

	/**
	 * 数据库名称
	 */
    @Column(name = "name",nullable = false)
    private String name;

	/**
	 * 数据库连接地址
	 */
    @Column(name = "jdbc_url",nullable = false)
    private String jdbcUrl;

	/**
	 * 数据库密码
	 */
    @Column(name = "pwd",nullable = false)
    private String pwd;

	/**
	 * 用户名
	 */
    @Column(name = "user_name",nullable = false)
    private String userName;

//	@CreationTimestamp
//	private Timestamp createTime;

    public void copy(Database source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
