package vip.efactory.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.entity.QiniuConfig;
import vip.efactory.entity.QiniuContent;
import vip.efactory.service.QiNiuService;
import vip.efactory.service.dto.QiniuQueryCriteria;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送邮件
 */
@Slf4j
@RestController
@RequestMapping("api")
public class QiniuController extends BaseController<QiniuConfig, QiNiuService> {

    @GetMapping(value = "/qiNiuConfig")
    public ResponseEntity get() {
        return new ResponseEntity(entityService.find(), HttpStatus.OK);
    }

    @Log("配置七牛云存储")
    @PutMapping(value = "/qiNiuConfig")
    public ResponseEntity emailConfig(@Validated @RequestBody QiniuConfig qiniuConfig) {
        entityService.update(qiniuConfig);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询文件")
    @GetMapping(value = "/qiNiuContent")
    public ResponseEntity getRoles(QiniuQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(entityService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    /**
     * 上传文件到七牛云
     *
     * @param file
     * @return
     */
    @Log("上传文件")
    @PostMapping(value = "/qiNiuContent")
    public ResponseEntity upload(@RequestParam MultipartFile file) {
        QiniuContent qiniuContent = entityService.upload(file, entityService.find());
        Map map = new HashMap(3);
        map.put("id", qiniuContent.getId());
        map.put("errno", 0);
        map.put("data", new String[]{qiniuContent.getUrl()});
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 同步七牛云数据到数据库
     *
     * @return
     */
    @Log("同步七牛云数据")
    @PostMapping(value = "/qiNiuContent/synchronize")
    public ResponseEntity synchronize() {
        log.warn("REST request to synchronize qiNiu : {}");
        entityService.synchronize(entityService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 下载七牛云文件
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Log("下载文件")
    @GetMapping(value = "/qiNiuContent/download/{id}")
    public ResponseEntity download(@PathVariable Long id) {
        Map map = new HashMap(1);
        map.put("url", entityService.download(entityService.findByContentId(id), entityService.find()));
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 删除七牛云文件
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Log("删除文件")
    @DeleteMapping(value = "/qiNiuContent/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        entityService.delete(entityService.findByContentId(id), entityService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除多张图片
     *
     * @param ids
     * @return
     */
    @Log("删除图片")
    @DeleteMapping(value = "/qiNiuContent")
    public ResponseEntity deleteAll(@RequestBody Long[] ids) {
        entityService.deleteAll(ids, entityService.find());
        return new ResponseEntity(HttpStatus.OK);
    }
}
