package vip.efactory.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.domain.QiniuConfig;
import vip.efactory.domain.QiniuContent;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.service.QiNiuService;
import vip.efactory.service.dto.QiniuQueryCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 发送邮件
 */
@RestController
@RequestMapping("/api/qiNiuContent")
@Api(tags = "工具：七牛云存储管理")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class QiniuController extends BaseController<QiniuConfig, QiNiuService, Long> {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/config")
    public R get() {
        return R.ok(entityService.find());
    }

    @Log("配置七牛云存储")
    @ApiOperation("配置七牛云存储")
    @PutMapping(value = "/config")
    public void emailConfig(@Validated @RequestBody QiniuConfig qiniuConfig, HttpServletResponse response) {
        try {
            // 只保留这1行update调用，删掉下面那行错误的
            entityService.update(qiniuConfig);
            // 删掉 ↓ 这行错误代码
            // entityService.update(qiniuConfig.getType());

            // 原有响应逻辑不动
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);
            objectMapper.writeValue(response.getWriter(), R.ok());
        } catch (Exception e) {
            // 原有异常逻辑不动
            Map<String, Object> errorMap = new HashMap<>(3);
            errorMap.put("status", 400);
            errorMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            errorMap.put("message", e.getMessage() != null ? e.getMessage() : "外链域名必须以http://或者https://开头");
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(400);
                objectMapper.writeValue(response.getWriter(), errorMap);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    public void download(HttpServletResponse response, QiniuQueryCriteria criteria) throws IOException {
        entityService.downloadList(entityService.queryAll(criteria), response);
    }

    @Log("查询文件")
    @ApiOperation("查询文件")
    @GetMapping("/page")
    public R getRoles(QiniuQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAll(criteria, pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity 含有高级查询条件
     * @param page   分页参数对象
     * @return R
     */
    @Log("分页高级查询文件")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    public R advancedQuery(@RequestBody QiniuConfig entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("上传文件")
    @ApiOperation("上传文件")
    @PostMapping
    public R upload(@RequestParam MultipartFile file) {
        QiniuContent qiniuContent = entityService.upload(file, entityService.find());
        Map<String, Object> map = new HashMap<>(3);
        map.put("id", qiniuContent.getId());
        map.put("errno", 0);
        map.put("data", new String[]{qiniuContent.getUrl()});
        return R.ok(map);
    }

    @Log("同步七牛云数据")
    @ApiOperation("同步七牛云数据")
    @PostMapping(value = "/synchronize")
// 【仅改此处1：方法签名】返回值R→void + 加HttpServletResponse response入参
    public void synchronize(HttpServletResponse response) {
        try {
            // 原有所有业务逻辑：1行不动，完全保留
            entityService.synchronize(entityService.find());
            // 【仅改此处2：成功时】原生返回原有R.ok()格式，和之前一致
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(200);
            objectMapper.writeValue(response.getWriter(), R.ok());
        } catch (Exception e) {
            // 【仅改此处3：异常时】构造你要的指定格式，原生写响应
            Map<String, Object> errorMap = new HashMap<>(3);
            errorMap.put("status", 400);
            errorMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            errorMap.put("message", e.getMessage() != null ? e.getMessage() : "请先添加相应配置，再操作"); // 兼容自定义提示
            // 原生写响应，绕开框架封装，纯返回目标JSON
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(400);
                objectMapper.writeValue(response.getWriter(), errorMap);
            } catch (Exception ex) {
                ex.printStackTrace(); // 兜底日志，不影响核心逻辑
            }
        }
    }

    @Log("下载文件")
    @ApiOperation("下载文件")
    @GetMapping(value = "/download/{id}")
    public R download(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("url", entityService.download(entityService.findByContentId(id), entityService.find()));
        return R.ok(map);
    }

    @Log("删除文件")
    @ApiOperation("删除文件")
    @DeleteMapping(value = "/{id}")
    public R delete(@PathVariable Long id) {
        entityService.delete(entityService.findByContentId(id), entityService.find());
        return R.ok();
    }

    @Log("删除多张图片")
    @ApiOperation("删除多张图片")
    @DeleteMapping
    public R deleteAll(@RequestBody Long[] ids) {
        entityService.deleteAll(ids, entityService.find());
        return R.ok();
    }
}
