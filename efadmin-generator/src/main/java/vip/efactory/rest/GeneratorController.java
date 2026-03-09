package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import vip.efactory.common.base.utils.R;
import vip.efactory.domain.ColumnInfo;
import vip.efactory.exception.BadRequestException;
import vip.efactory.service.GenConfigService;
import vip.efactory.service.GeneratorService;
import vip.efactory.utils.PageUtil;
import com.fasterxml.jackson.databind.ObjectMapper; // Spring自带JSON工具
import org.springframework.beans.factory.annotation.Autowired; // 注入注解
import java.time.LocalDateTime; // 时间格式化
import java.time.format.DateTimeFormatter; // 时间格式器
import java.util.HashMap; // 构造错误Map
import java.util.Map;

import java.util.List;

import static cn.hutool.core.util.PageUtil.transToStartEnd;

@RestController
@RequestMapping("/api/generator")
@Api(tags = "系统：代码生成管理")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class GeneratorController {
    private final GeneratorService generatorService;
    private final GenConfigService genConfigService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    public GeneratorController(GeneratorService generatorService, GenConfigService genConfigService) {
        this.generatorService = generatorService;
        this.genConfigService = genConfigService;
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables/all")
    public R getTables(){
        return R.ok(generatorService.getTables());
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables")
    public R getTables(@RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "0")Integer page,
                       @RequestParam(defaultValue = "10")Integer size){
        int[] startEnd = transToStartEnd(page, size);
        return R.ok(generatorService.getTables(name,startEnd));
    }

    @ApiOperation("查询字段数据")
    @GetMapping(value = "/columns")
    public R getTables(@RequestParam String tableName){
        List<ColumnInfo> columnInfos = generatorService.getColumns(tableName);
        return R.ok(PageUtil.toPage(columnInfos,columnInfos.size()));
    }

    @ApiOperation("保存字段数据")
    @PutMapping
    public R save(@RequestBody List<ColumnInfo> columnInfos){
        generatorService.save(columnInfos);
        return R.ok();
    }

    @ApiOperation("同步字段数据")
    @PostMapping(value = "sync")
    public R sync(@RequestBody List<String> tables){
        for (String table : tables) {
            generatorService.sync(generatorService.getColumns(table), generatorService.query(table));
        }
        return R.ok();
    }

    @ApiOperation("生成代码")
    @PostMapping(value = "/{tableName}/{type}")
// 仅改1：返回值从R→void（原生写响应无需返回值）
    public void generator(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response){
        try {
            // 原有所有业务逻辑：1行不动，完全保留
            if(!generatorEnabled && type == 0){
                throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
            }
            switch (type){
                // 生成代码
                case 0: generatorService.generator(genConfigService.find(tableName), generatorService.getColumns(tableName));
                    break;
                // 预览
                case 1:
                    // 仅改2：预览场景原有return R，改为原生写响应
                    R previewResult = generatorService.preview(genConfigService.find(tableName), generatorService.getColumns(tableName));
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(200);
                    objectMapper.writeValue(response.getWriter(), previewResult);
                    break;
                // 打包
                case 2: generatorService.download(genConfigService.find(tableName), generatorService.getColumns(tableName), request, response);
                    break;
                default: throw new BadRequestException("没有这个选项");
            }
        } catch (Exception e) {
            // 仅改3：异常时构造你要的指定格式，原生写响应（无任何框架封装）
            Map<String, Object> errorMap = new HashMap<>(3);
            errorMap.put("status", 400);
            errorMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            // 优先取原始异常信息，无信息时返回指定提示
            errorMap.put("message", e.getMessage() != null ? e.getMessage() : "请先配置生成器");

            // 原生写响应，绕开R类封装
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(400);
                objectMapper.writeValue(response.getWriter(), errorMap);
            } catch (Exception ex) {
                ex.printStackTrace(); // 兜底日志，不影响核心逻辑
            }
        }
    }
}
