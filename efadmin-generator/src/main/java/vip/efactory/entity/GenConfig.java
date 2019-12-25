package vip.efactory.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.ejpa.base.valid.Update;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 代码生成配置
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "sys_gen_config")
public class GenConfig extends BaseEntity<Long> {

    public GenConfig(String tableName) {
        this.cover = false;
        this.moduleName = "eladmin-system";
        this.tableName = tableName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    @NotBlank
    private String tableName;

    /** 接口名称 **/
    private String apiAlias;

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
