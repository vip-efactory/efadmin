package vip.efactory.modules.system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class MenuMetaVo implements Serializable {

    private String title;

    private String icon;
}
