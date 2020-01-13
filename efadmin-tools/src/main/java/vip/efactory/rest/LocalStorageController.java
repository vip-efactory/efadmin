package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.aop.log.Log;
import vip.efactory.domain.LocalStorage;
import vip.efactory.ejpa.utils.R;
import vip.efactory.service.LocalStorageService;
import vip.efactory.service.dto.LocalStorageQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Api(tags = "工具：本地存储管理")
@RestController
@RequestMapping("/api/localStorage")
public class LocalStorageController {

    private final LocalStorageService localStorageService;

    @ApiOperation("查询文件")
    @GetMapping
    @PreAuthorize("@p.check('storage:list')")
    public R getLocalStorages(LocalStorageQueryCriteria criteria, Pageable pageable){
        return R.ok(localStorageService.queryAll(criteria,pageable));
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('storage:list')")
    public void download(HttpServletResponse response, LocalStorageQueryCriteria criteria) throws IOException {
        localStorageService.download(localStorageService.queryAll(criteria), response);
    }

    @ApiOperation("上传文件")
    @PostMapping
    @PreAuthorize("@p.check('storage:add')")
    public R create(@RequestParam String name, @RequestParam("file") MultipartFile file){
        return R.ok(localStorageService.create(name, file));    // 原http的状态码是已创建！
    }

    @ApiOperation("修改文件")
    @PutMapping
    @PreAuthorize("@p.check('storage:edit')")
    public R update(@Validated @RequestBody LocalStorage resources){
        localStorageService.update(resources);
        return R.ok();  // 原HttpStatus.NO_CONTENT
    }

    @Log("多选删除")
    @DeleteMapping
    @ApiOperation("多选删除")
    public R deleteAll(@RequestBody Long[] ids) {
        localStorageService.deleteAll(ids);
        return R.ok();
    }
}