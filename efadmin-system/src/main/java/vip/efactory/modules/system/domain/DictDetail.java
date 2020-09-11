package vip.efactory.modules.system.domain;

import lombok.Getter;
import lombok.Setter;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "sys_dict_detail")
public class DictDetail extends BaseEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id {property.not.allow.empty}", groups = Update.class)  // 意味着，updateById更新时id不允许为空
    private Long id;

    /** 字典标签 */
    @Column(name = "label",nullable = false)
    private String label;

    /** 字典值 */
    @Column(name = "value",nullable = false)
    private String value;

    /** 排序 */
    @Column(name = "sort")
    private String sort = "999";

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dict_id")
    private Dict dict;

}
