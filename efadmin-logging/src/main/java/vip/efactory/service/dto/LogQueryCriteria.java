package vip.efactory.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import vip.efactory.annotation.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日志查询类
 */
@Data
public class LogQueryCriteria {

    @Query(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    @Query
    private String logType;

    @Query(type = Query.Type.BETWEEN)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> createTime;

    // 手动添加setter（覆盖Lombok生成的方法，确保编译器能识别）
    public void setLogType(String logType) {
        this.logType = logType;
    }
    // 手动添加getter（覆盖Lombok生成的方法，确保编译器能识别）
    public String getLogType() {
        return logType;
        }

    public void setBlurry(String blurry) {
        this.blurry = blurry;
    }
}
