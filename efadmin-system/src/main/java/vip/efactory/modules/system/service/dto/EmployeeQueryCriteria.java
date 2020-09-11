package vip.efactory.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vip.efactory.annotation.Query;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author dbdu
 * @date 2019-07-21
 */
@Data
public class EmployeeQueryCriteria {

    // 精确
    @Query
    private Long id;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String address;

    // 精确
    @Query
    private String avatar;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthday;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String code;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String email;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String idNumber;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String phone;

    // 精确
    @Query
    private Integer status;

    // 精确
    @Query
    private Long deptId;

    // 精确
    @Query
    private Long jobId;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String creatorNum;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    // 模糊
    @Query(type = Query.Type.INNER_LIKE)
    private String updaterNum;
}
