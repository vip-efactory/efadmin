package vip.efactory.modules.mnt.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.utils.R;
import vip.efactory.modules.mnt.domain.Deploy;
import vip.efactory.modules.mnt.domain.DeployHistory;
import vip.efactory.modules.mnt.service.DeployService;
import vip.efactory.modules.mnt.service.dto.DeployQueryCriteria;
import vip.efactory.utils.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Api(tags = "部署管理")
@RestController
@RequestMapping("/api/deploy")
public class DeployController {

	private String fileSavePath = System.getProperty("java.io.tmpdir");

    private final DeployService deployService;

	public DeployController(DeployService deployService) {
		this.deployService = deployService;
	}

	@Log("导出部署数据")
	@ApiOperation("导出部署数据")
	@GetMapping(value = "/download")
	@PreAuthorize("@p.check('database:list')")
	public void download(HttpServletResponse response, DeployQueryCriteria criteria) throws IOException {
		deployService.download(deployService.queryAll(criteria), response);
	}

	@Log("查询部署")
    @ApiOperation(value = "查询部署")
    @GetMapping
	@PreAuthorize("@p.check('deploy:list')")
    public R getDeploys(DeployQueryCriteria criteria, Pageable pageable){
    	return R.ok(deployService.queryAll(criteria,pageable));
    }

    @Log("新增部署")
    @ApiOperation(value = "新增部署")
    @PostMapping
	@PreAuthorize("@p.check('deploy:add')")
    public R create(@Validated @RequestBody Deploy resources){
        return R.ok(deployService.create(resources));
    }

    @Log("修改部署")
    @ApiOperation(value = "修改部署")
    @PutMapping
	@PreAuthorize("@p.check('deploy:edit')")
    public R update(@Validated @RequestBody Deploy resources){
        deployService.update(resources);
        return R.ok();
    }

	@Log("删除部署")
	@ApiOperation(value = "删除部署")
	@DeleteMapping
	@PreAuthorize("@p.check('deploy:del')")
	public R delete(@RequestBody Set<Long> ids){
		deployService.delete(ids);
		return R.ok();
	}

	@Log("上传文件部署")
	@ApiOperation(value = "上传文件部署")
	@PostMapping(value = "/upload")
	@PreAuthorize("@p.check('deploy:edit')")
	public R upload(@RequestBody MultipartFile file, HttpServletRequest request)throws Exception{
		Long id = Long.valueOf(request.getParameter("id"));
		String fileName = "";
		if(file != null){
			fileName = file.getOriginalFilename();
			File deployFile = new File(fileSavePath+fileName);
			FileUtil.del(deployFile);
			file.transferTo(deployFile);
			//文件下一步要根据文件名字来
			deployService.deploy(fileSavePath+fileName ,id);
		}else{
			System.out.println("没有找到相对应的文件");
		}
		System.out.println("文件上传的原名称为:"+ Objects.requireNonNull(file).getOriginalFilename());
		Map<String,Object> map = new HashMap<>(2);
		map.put("errno",0);
		map.put("id",fileName);
		return R.ok(map);
	}
	@Log("系统还原")
	@ApiOperation(value = "系统还原")
	@PostMapping(value = "/serverReduction")
	@PreAuthorize("@p.check('deploy:edit')")
	public R serverReduction(@Validated @RequestBody DeployHistory resources){
		String result = deployService.serverReduction(resources);
		return R.ok(result);
	}
	@Log("服务运行状态")
	@ApiOperation(value = "服务运行状态")
	@PostMapping(value = "/serverStatus")
	@PreAuthorize("@p.check('deploy:edit')")
	public R serverStatus(@Validated @RequestBody Deploy resources){
		String result = deployService.serverStatus(resources);
    	return R.ok(result);
	}
	@Log("启动服务")
	@ApiOperation(value = "启动服务")
	@PostMapping(value = "/startServer")
	@PreAuthorize("@p.check('deploy:edit')")
	public R startServer(@Validated @RequestBody Deploy resources){
		String result = deployService.startServer(resources);
		return R.ok(result);
	}
	@Log("停止服务")
	@ApiOperation(value = "停止服务")
	@PostMapping(value = "/stopServer")
	@PreAuthorize("@p.check('deploy:edit')")
	public R stopServer(@Validated @RequestBody Deploy resources){
		String result = deployService.stopServer(resources);
		return R.ok(result);
	}
}
