package vip.efactory.modules.system.entity;

import lombok.Data;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "sys_dict")
public class Dict extends BaseEntity implements Serializable {

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    /**
     * 字典名称
     */
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    private String name;

//    /**
//     * 描述
//     */
//    @Column(name = "remark")
//    private String remark;

    @OneToMany(mappedBy = "dict", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<DictDetail> dictDetails;

    public @interface Update {
    }
}
