package vip.efactory.exception.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author dbdu
 * @date 19-7-10 上午7:10
 */
@Data
class ApiError {

    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(Integer status, String message) {
        this();
        this.status = status;
        this.message = message;
    }
}


