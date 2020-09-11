package vip.efactory.modules.system.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import vip.efactory.annotation.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Zheng Jie
 * 公共查询类
 */
@Data
public class RoleQueryCriteria {

    @Query(blurry = "name,remark")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> createTime;
}
