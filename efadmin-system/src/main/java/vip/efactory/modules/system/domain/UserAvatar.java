package vip.efactory.modules.system.domain;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vip.efactory.ejpa.base.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Zheng Jie
 * @date 2019年9月7日 16:16:59
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sys_user_avatar")
public class UserAvatar extends BaseEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realName;

    private String path;

    private String size;

    public UserAvatar(UserAvatar userAvatar, String realName, String path, String size) {
        this.id = ObjectUtil.isNotEmpty(userAvatar) ? userAvatar.getId() : null;
        this.realName = realName;
        this.path = path;
        this.size = size;
    }
}
