package vip.efactory.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.entity.BaseEntity;

/**
 * 上传成功后，存储结果
 */
@Data
@Entity
@Table(name = "sys_qiniu_content")
public class QiniuContent extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    /** 文件名 */
    @Column(name = "name")
    private String key;

    /**
     * 空间名
     */
    private String bucket;

    /**
     * 大小
     */
    private String size;

    /**
     * 文件地址
     */
    private String url;

    private String suffix;

    /** 空间类型：公开/私有 */
    private String type = "公开";

//    /**
//     * 更新时间
//     */
//    @UpdateTimestamp
//    @Column(name = "update_time")
//    private Timestamp updateTime;
}
