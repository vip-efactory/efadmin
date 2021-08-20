package vip.efactory.modules.system.rest;

import cn.hutool.core.lang.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.domain.Role;
import vip.efactory.modules.system.service.RoleService;
import vip.efactory.modules.system.service.UserService;
import vip.efactory.modules.system.service.dto.RoleDto;
import vip.efactory.modules.system.service.dto.RoleQueryCriteria;
import vip.efactory.modules.system.service.dto.RoleSmallDto;
import vip.efactory.modules.system.service.dto.UserDto;
import vip.efactory.utils.SecurityUtils;
import vip.efactory.utils.ThrowableUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Api(tags = "系统：角色管理")
@RestController
@RequestMapping("/api/roles")
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class RoleController extends BaseController<Role, RoleService, Long> {
    private static final String ENTITY_NAME = "role";

    private final UserService userService;

    @ApiOperation("获取单个role")
    @GetMapping(value = "/{id}")
    @PreAuthorize("@p.check('roles:list')")
    public R getRoles(@PathVariable Long id) {
        return R.ok(entityService.findById(id));
    }

    @Log("导出角色数据")
    @ApiOperation("导出角色数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('role:list')")
    public void download(HttpServletResponse response, RoleQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @ApiOperation("返回全部的角色")
    @GetMapping(value = "/all")
    @PreAuthorize("@p.check('roles:list','user:add','user:edit')")
    public R getAll(
            @PageableDefault(value = 2000, sort = { "level" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return R.ok(entityService.queryAll(pageable));
    }

    @Log("查询角色")
    @ApiOperation("查询角色")
    @GetMapping("/page")
    @PreAuthorize("@p.check('roles:list')")
    public R getRoles(RoleQueryCriteria criteria, Pageable pageable) {
        return R.ok(entityService.queryAll(criteria, pageable));
    }

    /**
     * Description: 高级查询
     *
     * @param entity 含有高级查询条件
     * @param page   分页参数对象
     * @return R
     */
    @Log("分页高级查询Role")
    @ApiOperation(value = "多条件组合查询,返回分页数据", notes = "默认每页25条记录,id字段降序")
    @PostMapping("/page")
    @PreAuthorize("@p.check('roles:list')")
    public R advancedQuery(@RequestBody Role entity,
            @PageableDefault(value = 25, sort = { "id" }, direction = Sort.Direction.DESC) Pageable page) {
        return super.advancedQueryByPage(page, entity);
    }

    @ApiOperation("获取用户级别")
    @GetMapping(value = "/level")
    public R getLevel() {
        return R.ok(Dict.create().set("level", getLevels(null)));
    }

    @Log("新增角色")
    @ApiOperation("新增角色")
    @PostMapping
    @PreAuthorize("@p.check('roles:add')")
    public R create(@Validated @RequestBody Role resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        getLevels(resources.getLevel());
        return R.ok(entityService.create(resources));
    }

    @Log("修改角色")
    @ApiOperation("修改角色")
    @PutMapping
    @PreAuthorize("@p.check('roles:edit')")
    public R update(@Validated(Update.class) @RequestBody Role resources) {
        getLevels(resources.getLevel());
        entityService.update2(resources);
        return R.ok();
    }

    @Log("修改角色菜单")
    @ApiOperation("修改角色菜单")
    @PutMapping(value = "/menu")
    @PreAuthorize("@p.check('roles:edit')")
    public R updateMenu(@RequestBody Role resources) {
        RoleDto role = entityService.findDtoById(resources.getId());
        getLevels(role.getLevel());
        entityService.updateMenu(resources, role);
        return R.ok();
    }

    @Log("删除角色")
    @ApiOperation("删除角色")
    @DeleteMapping
    @PreAuthorize("@p.check('roles:del')")
    public R delete(@RequestBody Set<Long> ids) {
        for (Long id : ids) {
            RoleDto role = entityService.findDtoById(id);
            getLevels(role.getLevel());
        }
        try {
            entityService.delete(ids);
        } catch (Exception e) {
            ThrowableUtil.throwForeignKeyException(e, "所选角色存在用户关联，请取消关联后再试");
        }
        return R.ok();
    }

    /**
     * 获取用户的角色级别
     *
     * @return /
     */
    private int getLevels(Integer level) {
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        List<Integer> levels = entityService.findByUsersId(user.getId()).stream().map(RoleSmallDto::getLevel)
                .collect(Collectors.toList());
        int min = Collections.min(levels);
        if (level != null && level < min) {
            throw new BadRequestException("权限不足，你的角色级别：" + min + "，低于操作的角色级别：" + level);
        }
        return min;
    }
}
