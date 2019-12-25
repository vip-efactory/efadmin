package vip.efactory.entity;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;
import vip.efactory.ejpa.base.valid.Update;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 上传成功后，存储结果
 */
@Data
@Entity
@Table(name = "sys_qiniu_content")
public class QiniuContent extends BaseEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    /**
     * 文件名，如qiniu.jpg
     */
    @Column(name = "name", unique = false)
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

    /**
     * 空间类型：公开/私有
     */
    private String type = "公开";

//    /**
//     * 更新时间
//     */
//    @UpdateTimestamp
//    @Column(name = "update_time")
//    private Timestamp updateTime;
}
