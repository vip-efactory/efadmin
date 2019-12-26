package vip.efactory.modules.system.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class DictDto implements Serializable {

    private Long id;

    private String name;

    private String remark;

    private List<DictDetailDto> dictDetails;

    private Timestamp createTime;
}
