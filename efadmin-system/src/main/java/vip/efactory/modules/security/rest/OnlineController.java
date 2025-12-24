package vip.efactory.modules.security.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.modules.security.service.OnlineUserService;

import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/auth/online")
@Tag(name = "系统：在线用户管理") // 替换@Api为@Tag
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class OnlineController {

    private final OnlineUserService onlineUserService;

    @Operation(summary = "查询在线用户") // 替换@ApiOperation
    @GetMapping
    @PreAuthorize("@p.check()")
    public R getAll(String filter, Pageable pageable) {
        return R.ok(onlineUserService.getAll(filter, pageable));
    }

    @Log("导出数据")
    @Operation(summary = "导出数据") // 替换@ApiOperation
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check()")
    public void download(HttpServletResponse response, String filter) throws IOException {
        onlineUserService.download(onlineUserService.getAll(filter), response);
    }

    @Operation(summary = "踢出用户") // 替换@ApiOperation
    @DeleteMapping
    @PreAuthorize("@p.check()")
    public R delete(@RequestBody Set<String> keys) throws Exception {
        for (String key : keys) {
            onlineUserService.kickOut(key);
        }
        return R.ok();
    }
}