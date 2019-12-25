package vip.efactory.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.entity.Picture;
import vip.efactory.service.PictureService;
import vip.efactory.service.dto.PictureQueryCriteria;
import vip.efactory.utils.SecurityUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PictureController extends BaseController<Picture, PictureService> {

    @Log("查询图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_SELECT')")
    @GetMapping(value = "/pictures")
    public ResponseEntity getRoles(PictureQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity(entityService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     * @throws Exception
     */
    @Log("上传图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_UPLOAD')")
    @PostMapping(value = "/pictures")
    public ResponseEntity upload(@RequestParam MultipartFile file) {
        String userName = SecurityUtils.getUsername();
        Picture picture = entityService.upload(file, userName);
        Map map = new HashMap(3);
        map.put("errno", 0);
        map.put("id", picture.getId());
        map.put("data", new String[]{picture.getUrl()});
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 删除图片
     *
     * @param id
     * @return
     */
    @Log("删除图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_DELETE')")
    @DeleteMapping(value = "/pictures/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        entityService.delete(entityService.findById(id).get());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除多张图片
     *
     * @param ids
     * @return
     */
    @Log("删除图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_DELETE')")
    @DeleteMapping(value = "/pictures")
    public ResponseEntity deleteAll(@RequestBody Long[] ids) {
        entityService.deleteAll(ids);
        return new ResponseEntity(HttpStatus.OK);
    }
}
