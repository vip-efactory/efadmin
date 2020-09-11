package vip.efactory.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vip.efactory.common.base.utils.R;
import vip.efactory.domain.ColumnInfo;
import vip.efactory.exception.BadRequestException;
import vip.efactory.service.GenConfigService;
import vip.efactory.service.GeneratorService;
import vip.efactory.utils.PageUtil;

@RestController
@RequestMapping("/api/generator")
@Api(tags = "系统：代码生成管理")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class GeneratorController {
    private final GeneratorService generatorService;
    private final GenConfigService genConfigService;

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
        int[] startEnd = PageUtil.transToStartEnd(page+1, size);
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
    public R generator(@PathVariable String tableName, @PathVariable Integer type, HttpServletRequest request, HttpServletResponse response){
        if(!generatorEnabled && type == 0){
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }
        switch (type){
            // 生成代码
            case 0: generatorService.generator(genConfigService.find(tableName), generatorService.getColumns(tableName));
                break;
            // 预览
            case 1: return generatorService.preview(genConfigService.find(tableName), generatorService.getColumns(tableName));
            // 打包
            case 2: generatorService.download(genConfigService.find(tableName), generatorService.getColumns(tableName), request, response);
                break;
            default: throw new BadRequestException("没有这个选项");
        }
        // 注意此处一定要返回null，不能返回R.ok(),case0和2都自动接管了response，如果此时不返回null，控制台就会无法处理数据而报异常。
        return null;
    }
}
