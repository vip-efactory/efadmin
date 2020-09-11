package vip.efactory.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import vip.efactory.annotation.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@Data
public class LocalStorageQueryCriteria{

    @Query(blurry = "name,suffix,type,operate,size")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> createTime;
}
