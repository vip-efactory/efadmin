package vip.efactory.service.dto;

import lombok.Data;
import vip.efactory.annotation.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * sm.ms图床
 */
@Data
public class PictureQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String filename;

    @Query(type = Query.Type.INNER_LIKE)
    private String username;

    @Query(type = Query.Type.BETWEEN)
    private List<LocalDateTime> createTime;
}
