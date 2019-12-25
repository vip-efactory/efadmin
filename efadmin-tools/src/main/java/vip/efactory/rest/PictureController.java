package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pictures")
@Api(tags = "工具：免费图床管理")
public class PictureController extends BaseController<Picture, PictureService, Long> {

    @Log("查询图片")
    @PreAuthorize("@el.check('pictures:list')")
    @GetMapping
    @ApiOperation("查询图片")
    public ResponseEntity<Object> getRoles(PictureQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(entityService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('pictures:list')")
    public void download(HttpServletResponse response, PictureQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("上传图片")
    @PreAuthorize("@el.check('pictures:add')")
    @PostMapping
    @ApiOperation("上传图片")
    public ResponseEntity<Object> upload(@RequestParam MultipartFile file){
        String userName = SecurityUtils.getUsername();
        Picture picture = entityService.upload(file,userName);
        return new ResponseEntity<>(picture,HttpStatus.OK);
    }

    @Log("同步图床数据")
    @ApiOperation("同步图床数据")
    @PostMapping(value = "/synchronize")
    public ResponseEntity<Object> synchronize(){
        entityService.synchronize();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("多选删除图片")
    @ApiOperation("多选删除图片")
    @PreAuthorize("@el.check('pictures:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        entityService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
