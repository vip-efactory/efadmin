package vip.efactory.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class JobDto implements Serializable {

    /**
     * ID
     */
    private Long id;

    private Long sort;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Boolean enabled;

    private DeptDto dept;

    /**
     * 如果分公司存在相同部门，则显示上级部门名称
     */
    private String deptSuperiorName;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    public JobDto(String name, Boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
}
