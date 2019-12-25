package vip.efactory.entity;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.ejpa.base.valid.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 代码生成配置
 */
@Data
@Entity
@Table(name = "sys_gen_config")
public class GenConfig extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    /**
     * 包路径
     **/
    private String pack;

    /**
     * 模块名,efadmin-开头,例如efadmin-company,company就是模块名
     **/
    @Column(name = "module_name")
    private String moduleName;

    /**
     * 前端文件路径
     **/
    private String path;

    /**
     * 前端文件路径
     **/
    @Column(name = "api_path")
    private String apiPath;

    /**
     * 作者
     **/
    private String author;

    /**
     * 表前缀
     **/
    private String prefix;

    /**
     * 是否覆盖
     **/
    private Boolean cover;
}
