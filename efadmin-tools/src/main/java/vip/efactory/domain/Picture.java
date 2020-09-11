package vip.efactory.domain;

import java.io.Serializable;

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
 * sm.ms图床
 */
@Data
@Entity
@Table(name = "sys_picture")
public class Picture extends BaseEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    private String filename;

    private String url;

    private String size;

    private String height;

    private String width;

    /**
     * delete URl
     */
    @Column(name = "delete_url")
    private String delete;

    private String username;

    /** 用于检测文件是否重复 */
    private String md5Code;

//    @CreationTimestamp
//    @Column(name = "create_time")
//    private Timestamp createTime;

    @Override
    public String toString() {
        return "Picture{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
