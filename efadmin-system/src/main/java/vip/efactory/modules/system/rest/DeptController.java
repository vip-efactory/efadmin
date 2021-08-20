package vip.efactory.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.common.base.valid.Update;
import vip.efactory.config.DataScope;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.domain.Dept;
import vip.efactory.modules.system.service.DeptService;
import vip.efactory.modules.system.service.dto.DeptDto;
import vip.efactory.modules.system.service.dto.DeptQueryCriteria;
import vip.efactory.utils.ThrowableUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cn.hutool.core.collection.CollUtil.isNotEmpty;
import static cn.hutool.core.collection.CollUtil.sort;

@AllArgsConstructor
@RestController
@Api(tags = "系统：部门管理")
@RequestMapping("/api/dept")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class DeptController extends BaseController<Dept, DeptService, Long> {
    private static final String ENTITY_NAME = "dept";

    private final DataScope dataScope;

    @Log("导出部门数据")
    @ApiOperation("导出部门数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('dept:list')")
    public void download(HttpServletResponse response, DeptQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Log("查询部门")
    @ApiOperation("查询部门")
    @GetMapping("/all")
    @PreAuthorize("@p.check('user:list','dept:list')")
    public R getDepts(DeptQueryCriteria criteria) {
        // 数据权限
        criteria.setIds(dataScope.getDeptIds());
        List<DeptDto> deptDtos = entityService.queryAll(criteria);
        // 对sort属性进行升序排序
        sort(deptDtos, Comparator.comparing(DeptDto::getSort));
        return R.ok(entityService.buildTree(deptDtos));
    }

    /**
     * Description: 高级查询
     *
     * @param entity 含有高级查询条件
     * @return R
     */
    @Override
    @Log("高级查询部门")
    @ApiOperation(value = "多条件组合查询")
    @PostMapping("/all")
    @PreAuthorize("@p.check('user:list','dept:list')")
    public R advancedQuery(@RequestBody Dept entity) {
        List<Dept> entities = entityService.advancedQuery(entity);
        return R.ok(entityService.buildTree4Entites(entities));
    }

    @Log("新增部门")
    @ApiOperation("新增部门")
    @PostMapping
    @PreAuthorize("@p.check('dept:add')")
    public R create(@Validated @RequestBody Dept resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return R.ok(entityService.create(resources));
    }

    @Log("修改部门")
    @ApiOperation("修改部门")
    @PutMapping
    @PreAuthorize("@p.check('dept:edit')")
    public R update(@Validated(Update.class) @RequestBody Dept resources) {
        entityService.update2(resources);
        return R.ok();
    }

    @Log("删除部门")
    @ApiOperation("删除部门")
    @DeleteMapping
    @PreAuthorize("@p.check('dept:del')")
    public R delete(@RequestBody Set<Long> ids) {
        Set<DeptDto> deptDtos = new HashSet<>();
        for (Long id : ids) {
            List<Dept> deptList = entityService.findByPid(id);
            deptDtos.add(entityService.findDtoById(id));
            if (isNotEmpty(deptList)) {
                deptDtos = entityService.getDeleteDepts(deptList, deptDtos);
            }
        }
        try {
            entityService.delete(deptDtos);
        } catch (Exception e) {
            ThrowableUtil.throwForeignKeyException(e, "所选部门中存在岗位或者角色关联，请取消关联后再试");
        }
        return R.ok();
    }
}
