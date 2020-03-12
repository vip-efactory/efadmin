package vip.efactory.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.aop.log.Log;
import vip.efactory.domain.LocalStorage;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.domain.Picture;
import vip.efactory.ejpa.utils.R;
import vip.efactory.service.PictureService;
import vip.efactory.service.dto.PictureQueryCriteria;
import vip.efactory.utils.SecurityUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/pictures")
@Api(tags = "工具：免费图床管理")
public class PictureController extends BaseController<Picture, PictureService, Long> {

    @Log("查询图片")
    @PreAuthorize("@p.check('pictures:list')")
    @GetMapping("/page")
    @ApiOperation("查询图片")
    public R getRoles(PictureQueryCriteria criteria, Pageable pageable){
        return R.ok(entityService.queryAll(criteria,pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity 含有高级查询条件
     * @param page   分页参数对象
     * @return R
     */
    @Log("分页高级查询图片")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('pictures:list')")
    public R advancedQuery(@RequestBody Picture entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('pictures:list')")
    public void download(HttpServletResponse response, PictureQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("上传图片")
    @PreAuthorize("@p.check('pictures:add')")
    @PostMapping
    @ApiOperation("上传图片")
    public R upload(@RequestParam MultipartFile file){
        String userName = SecurityUtils.getUsername();
        Picture picture = entityService.upload(file,userName);
        return R.ok(picture);
    }

    @Log("同步图床数据")
    @ApiOperation("同步图床数据")
    @PostMapping(value = "/synchronize")
    public R synchronize(){
        entityService.synchronize();
        return R.ok();
    }

    @Log("多选删除图片")
    @ApiOperation("多选删除图片")
    @PreAuthorize("@p.check('pictures:del')")
    @DeleteMapping
    public R deleteAll(@RequestBody Long[] ids) {
        entityService.deleteAll(ids);
        return R.ok();
    }
}
