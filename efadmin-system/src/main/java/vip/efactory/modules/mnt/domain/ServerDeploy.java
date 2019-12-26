package vip.efactory.modules.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Table(name="sys_mnt_server")
public class ServerDeploy extends BaseEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String ip;

    private Integer port;

    private String account;

    private String password;

//    @CreationTimestamp
//    private Timestamp createTime;

    public void copy(ServerDeploy source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
