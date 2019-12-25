package vip.efactory.entity;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 上传成功后，存储结果
 */
@Data
@Entity
@Table(name = "sys_qiniu_content")
public class QiniuContent extends BaseEntity implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

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
