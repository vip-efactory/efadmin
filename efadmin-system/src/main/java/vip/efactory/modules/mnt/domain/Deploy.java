package vip.efactory.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Entity
@Data
@Table(name = "sys_mnt_deploy")
public class Deploy extends BaseEntity<Long> {

    /**
     * 部署编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 应用编号
     */
    @ManyToOne
    @JoinColumn(name = "app_id")
    private App app;

    /**
     * 服务器
     */
    @ManyToMany
    @JoinTable(name = "sys_mnt_deploy_server", joinColumns = {
            @JoinColumn(name = "deploy_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "server_id", referencedColumnName = "id") })
    private Set<ServerDeploy> deploys;

    public void copy(Deploy source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
