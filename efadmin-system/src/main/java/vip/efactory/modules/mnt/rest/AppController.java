package vip.efactory.modules.mnt.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.utils.R;
import vip.efactory.modules.mnt.domain.App;
import vip.efactory.modules.mnt.service.AppService;
import vip.efactory.modules.mnt.service.dto.AppQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@AllArgsConstructor
@Api(tags = "应用管理")
@RestController
@RequestMapping("/api/app")
public class AppController {
    private final AppService appService;

    @Log("导出应用数据")
    @ApiOperation("导出应用数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('app:list')")
    public void download(HttpServletResponse response, AppQueryCriteria criteria) throws IOException {
        appService.download(appService.queryAll(criteria), response);
    }

    @Log("查询应用")
    @ApiOperation(value = "查询应用")
    @GetMapping
	@PreAuthorize("@p.check('app:list')")
    public R getApps(AppQueryCriteria criteria, Pageable pageable){
        return R.ok(appService.queryAll(criteria,pageable));
    }

    @Log("新增应用")
    @ApiOperation(value = "新增应用")
    @PostMapping
	@PreAuthorize("@p.check('app:add')")
    public R create(@Validated @RequestBody App resources){
        return R.ok(appService.create(resources));
    }

    @Log("修改应用")
    @ApiOperation(value = "修改应用")
    @PutMapping
	@PreAuthorize("@p.check('app:edit')")
    public R update(@Validated @RequestBody App resources){
        appService.update(resources);
        return R.ok();
    }

    @Log("删除应用")
    @ApiOperation(value = "删除应用")
	@DeleteMapping
	@PreAuthorize("@p.check('app:del')")
    public R delete(@RequestBody Set<Long> ids){
        appService.delete(ids);
        return R.ok();
    }
}
