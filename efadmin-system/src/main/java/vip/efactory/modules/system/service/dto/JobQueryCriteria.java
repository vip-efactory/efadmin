package vip.efactory.modules.system.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import vip.efactory.annotation.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class JobQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query(propName = "id", joinName = "dept")
    private Long deptId;

    @Query(propName = "id", joinName = "dept", type = Query.Type.IN)
    private Set<Long> deptIds;

    @Query(type = Query.Type.BETWEEN)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> createTime;
}
