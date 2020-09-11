package vip.efactory.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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

/**
 * 发送邮件
 */
@RestController
@RequestMapping("/api/qiNiuContent")
@Api(tags = "工具：七牛云存储管理")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class QiniuController extends BaseController<QiniuConfig, QiNiuService, Long> {

    @GetMapping(value = "/config")
    public R get() {
        return R.ok(entityService.find());
    }

    @Log("配置七牛云存储")
    @ApiOperation("配置七牛云存储")
    @PutMapping(value = "/config")
    public R emailConfig(@Validated @RequestBody QiniuConfig qiniuConfig) {
        entityService.update(qiniuConfig);
        entityService.update(qiniuConfig.getType());
        return R.ok();
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
    public R synchronize() {
        entityService.synchronize(entityService.find());
        return R.ok();
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
