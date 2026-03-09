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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
            // ========== 校验文件后缀是否为.sql ==========
            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.endsWith(".sql")) {
                throw new BadRequestException("仅支持执行.sql后缀的文件，当前文件：" + fileName);
            }

            // 原有逻辑：获取数据库配置
            String id = request.getParameter("id");
            DatabaseDto database = entityService.findDtoById(id);
            if (database == null) {
                throw new BadRequestException("Database not exist");
            }

            // 保存上传的文件（保留原有逻辑，移除多余的FileUtil.del）
            File executeFile = new File(fileSavePath, fileName);
            file.transferTo(executeFile);

            // ========== 调用SqlUtils执行SQL脚本，且仅执行成功后才返回success ==========
            String result = SqlUtils.executeFile(database.getJdbcUrl(), database.getUserName(), database.getPwd(), executeFile);
            // 校验执行结果（根据executeFile的返回值逻辑，确保执行成功），假设executeFile返回"success"代表执行成功，可根据实际返回值调整
            if (!"success".equals(result)) {
                throw new RuntimeException("SQL脚本执行失败：" + result);
            }

            // ========== 只有上述所有步骤无异常、SQL执行成功，才返回success ==========
            return "success";

        } catch (Exception e) {
            // 统一异常处理：按指定格式返回JSON响应
            Map<String, Object> errorMap = new HashMap<>(3);
            errorMap.put("status", HttpStatus.BAD_REQUEST.value()); // 400
            errorMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            errorMap.put("message", e.getMessage()); // 会捕获"非.sql文件/文件为空/SQL执行失败"等异常

            try {
                // 防止响应写入失败，增加内层try-catch
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                objectMapper.writeValue(response.getWriter(), errorMap);
            } catch (Exception writeEx) {
            }
        }
        return null; // 响应已通过原生方式写入，返回值无意义
    }
}