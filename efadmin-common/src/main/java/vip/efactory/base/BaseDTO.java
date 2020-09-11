package vip.efactory.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Zheng Jie
 * @Date 2019年10月24日20:48:53
 */
@Getter
@Setter
public class BaseDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return "BaseDTO{" +
                "isDelete=" + isDelete +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
