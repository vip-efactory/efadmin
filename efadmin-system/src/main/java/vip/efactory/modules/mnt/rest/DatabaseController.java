package vip.efactory.modules.mnt.rest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.mnt.domain.Database;
import vip.efactory.modules.mnt.service.DatabaseService;
import vip.efactory.modules.mnt.service.dto.DatabaseDto;
import vip.efactory.modules.mnt.service.dto.DatabaseQueryCriteria;
import vip.efactory.modules.mnt.util.SqlUtils;
import vip.efactory.utils.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@AllArgsConstructor
@Tag(name = "数据库管理") // 替换@Api为@Tag
@RestController
@RequestMapping("/api/database")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class DatabaseController extends BaseController<Database, DatabaseService, String> {
    private static String fileSavePath = System.getProperty("java.io.tmpdir");
    private final ObjectMapper objectMapper;

    @Log("导出数据库数据")
    @Operation(summary = "导出数据库数据") // 替换@ApiOperation
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('database:list')")
    public void download(HttpServletResponse response, DatabaseQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询数据库")
    @Operation(summary = "查询数据库") // 替换@ApiOperation(value = "...")
    @GetMapping("/page")
    @PreAuthorize("@p.check('database:list')")
    public R getDatabases(DatabaseQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAll(criteria, pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity 含有高级查询条件
     * @param page   分页参数对象
     * @return R
     */
    @Log("分页高级查询数据库")
    @Operation(summary = "多条件组合查询,返回分页数据", description = "默认每页25条记录,id字段降序") // 替换@ApiOperation(value + notes)
    @PostMapping("/page")
    @PreAuthorize("@p.check('database:list')")
    public R advancedQuery(@RequestBody Database entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("新增数据库")
    @Operation(summary = "新增数据库") // 替换@ApiOperation(value = "...")
    @PostMapping
    @PreAuthorize("@p.check('database:add')")
    public R create(@Validated @RequestBody Database resources) {
        return R.ok(entityService.create(resources));
    }

    @Log("修改数据库")
    @Operation(summary = "修改数据库") // 替换@ApiOperation(value = "...")
    @PutMapping
    @PreAuthorize("@p.check('database:edit')")
    public R update(@Validated @RequestBody Database resources) {
        entityService.update2(resources);
        return R.ok();
    }

    @Log("删除数据库")
    @Operation(summary = "删除数据库") // 替换@ApiOperation(value = "...")
    @DeleteMapping
    @PreAuthorize("@p.check('database:del')")
    public R delete(@RequestBody Set<String> ids) {
        entityService.delete(ids);
        return R.ok();
    }

    @Log("测试数据库链接")
    @Operation(summary = "测试数据库链接") // 替换@ApiOperation(value = "...")
    @PostMapping("/testConnect")
    @PreAuthorize("@p.check('database:testConnect')")
    public R testConnect(@Validated @RequestBody Database resources) {
        return R.ok(entityService.testConnection(resources));
    }

    @Log("执行SQL脚本")
    @Operation(summary = "执行SQL脚本")
    @PostMapping(value = "/upload")
    @PreAuthorize("@p.check('database:add')")
// 【仅改此处1：方法签名】返回值R→void + 加HttpServletResponse response入参（原有入参全保留）
    public void upload(@RequestBody MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // 原有所有业务逻辑：1行不动，完全保留
            String id = request.getParameter("id");
            DatabaseDto database = entityService.findDtoById(id);
            String fileName;
            if (database != null) {
                fileName = file.getOriginalFilename();
                File executeFile = new File(fileSavePath + fileName);
                FileUtil.del(executeFile);
                file.transferTo(executeFile);
                String result = SqlUtils.executeFile(database.getJdbcUrl(), database.getUserName(), database.getPwd(), executeFile);
                // 【仅改此处2：成功时】原生返回成功结果，和原有R.ok(result)一致
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(200);
                objectMapper.writeValue(response.getWriter(), R.ok(result));
            } else {
                throw new BadRequestException("Database not exist");
            }
        } catch (Exception e) {
            // 【仅改此处3：异常时】构造你要的指定格式，原生写响应，无任何框架封装
            Map<String, Object> errorMap = new HashMap<>(3);
            errorMap.put("status", 400);
            errorMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            errorMap.put("message", e.getMessage()); // 自动捕获"权限不够/文件不存在"等异常信息
            // 原生写响应，绕开框架封装，纯返回目标JSON
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(400);
            objectMapper.writeValue(response.getWriter(), errorMap);
        }
    }
}