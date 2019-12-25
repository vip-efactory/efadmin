package vip.efactory.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeptSmallDTO implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;
}
