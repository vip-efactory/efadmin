package vip.efactory.modules.mnt.rest;

import com.jcraft.jsch.JSchException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.ejpa.utils.R;
import vip.efactory.modules.mnt.domain.App;
import vip.efactory.modules.mnt.domain.Deploy;
import vip.efactory.modules.mnt.domain.DeployHistory;
import vip.efactory.modules.mnt.service.DeployService;
import vip.efactory.modules.mnt.service.dto.DeployQueryCriteria;
import vip.efactory.modules.system.service.DeptService;
import vip.efactory.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Api(tags = "部署管理")
@RestController
@RequestMapping("/api/deploy")
public class DeployController extends BaseController<Deploy, DeployService, Long> {
    private static String fileSavePath = System.getProperty("java.io.tmpdir");

    @Log("导出部署数据")
    @ApiOperation("导出部署数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('database:list')")
    public void download(HttpServletResponse response, DeployQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询部署")
    @ApiOperation(value = "查询部署")
    @GetMapping("/page")
    @PreAuthorize("@p.check('deploy:list')")
    public R getDeploys(DeployQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAll(criteria, pageable));
    }

	/**
	 * Description: 高级查询
	 *
	 * @param entity 含有高级查询条件
	 * @param page   分页参数对象
	 * @return R
	 */
	@Log("分页高级查询部署")
	@ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
	@PostMapping("/page")
	@PreAuthorize("@p.check('deploy:list')")
	public R advancedQuery(@RequestBody Deploy entity, @PageableDefault(value = 25, sort = {"id"}, direction = Sort.Direction.DESC) Pageable page) {
		return super.advancedQueryByPage(page, entity);
	}

    @Log("新增部署")
    @ApiOperation(value = "新增部署")
    @PostMapping
    @PreAuthorize("@p.check('deploy:add')")
    public R create(@Validated @RequestBody Deploy resources) {
        return R.ok(entityService.create(resources));
    }

    @Log("修改部署")
    @ApiOperation(value = "修改部署")
    @PutMapping
    @PreAuthorize("@p.check('deploy:edit')")
    public R update(@Validated @RequestBody Deploy resources) {
        entityService.update2(resources);
        return R.ok();
    }

    @Log("删除部署")
    @ApiOperation(value = "删除部署")
    @DeleteMapping
    @PreAuthorize("@p.check('deploy:del')")
    public R delete(@RequestBody Set<Long> ids) {
        entityService.delete(ids);
        return R.ok();
    }

    @Log("上传文件部署")
    @ApiOperation(value = "上传文件部署")
    @PostMapping(value = "/upload")
    @PreAuthorize("@p.check('deploy:edit')")
    public R upload(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        String fileName = "";
        if (file != null) {
            fileName = file.getOriginalFilename();
            File deployFile = new File(fileSavePath + fileName);
            FileUtil.del(deployFile);
            file.transferTo(deployFile);
            //文件下一步要根据文件名字来
            entityService.deploy(fileSavePath + fileName, id);
        } else {
            System.out.println("没有找到相对应的文件");
        }
        System.out.println("文件上传的原名称为:" + Objects.requireNonNull(file).getOriginalFilename());
        Map<String, Object> map = new HashMap<>(2);
        map.put("errno", 0);
        map.put("id", fileName);
        return R.ok(map);
    }

    @Log("系统还原")
    @ApiOperation(value = "系统还原")
    @PostMapping(value = "/serverReduction")
    @PreAuthorize("@p.check('deploy:edit')")
    public R serverReduction(@Validated @RequestBody DeployHistory resources) {
        try {
            String result = entityService.serverReduction(resources);
            return R.ok(result);
        } catch (JSchException e) {
            e.printStackTrace();
            return R.error(e);
        }
    }

    @Log("服务运行状态")
    @ApiOperation(value = "服务运行状态")
    @PostMapping(value = "/serverStatus")
    @PreAuthorize("@p.check('deploy:edit')")
    public R serverStatus(@Validated @RequestBody Deploy resources) {
        try {
            String result = entityService.serverStatus(resources);
            return R.ok(result);
        } catch (JSchException e) {
            e.printStackTrace();
            return R.error(e);
        }
    }

    @Log("启动服务")
    @ApiOperation(value = "启动服务")
    @PostMapping(value = "/startServer")
    @PreAuthorize("@p.check('deploy:edit')")
    public R startServer(@Validated @RequestBody Deploy resources) {
        try {
            String result = entityService.startServer(resources);
            return R.ok(result);
        } catch (JSchException e) {
            e.printStackTrace();
            return R.error(e);
        }

    }

    @Log("停止服务")
    @ApiOperation(value = "停止服务")
    @PostMapping(value = "/stopServer")
    @PreAuthorize("@p.check('deploy:edit')")
    public R stopServer(@Validated @RequestBody Deploy resources) {
        try {
            String result = entityService.stopServer(resources);
            return R.ok(result);
        } catch (JSchException e) {
            e.printStackTrace();
            return R.error(e);
        }
    }
}
