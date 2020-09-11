package vip.efactory.modules.system.rest;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.controller.BaseController;

import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.domain.Menu;
import vip.efactory.modules.system.service.MenuService;
import vip.efactory.modules.system.service.RoleService;
import vip.efactory.modules.system.service.UserService;
import vip.efactory.modules.system.service.dto.MenuDto;
import vip.efactory.modules.system.service.dto.MenuQueryCriteria;
import vip.efactory.modules.system.service.dto.UserDto;
import vip.efactory.utils.SecurityUtils;

@AllArgsConstructor
@Api(tags = "系统：菜单管理")
@RestController
@RequestMapping("/api/menus")
@SuppressWarnings(value = { "rawtypes", "unchecked" }) // 压制原生类型的警告
public class MenuController extends BaseController<Menu, MenuService, Long> {
    private static final String ENTITY_NAME = "menu";

    private final UserService userService;
    private final RoleService roleService;

    @Log("导出菜单数据")
    @ApiOperation("导出菜单数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('menu:list')")
    public void download(HttpServletResponse response, MenuQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @ApiOperation("获取前端所需菜单")
    @GetMapping(value = "/build")
    public R buildMenus() {
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        List<MenuDto> menuDtoList = entityService.findByRoles(roleService.findByUsersId(user.getId()));
        List<MenuDto> menuDtos = (List<MenuDto>) entityService.buildTree(menuDtoList).get("content");
        return R.ok(entityService.buildMenus(menuDtos));
    }

    @ApiOperation("返回全部的菜单")
    @GetMapping(value = "/tree")
    @PreAuthorize("@p.check('menu:list','roles:list')")
    public R getMenuTree() {
        return R.ok(entityService.getMenuTree(entityService.findByPid(0L)));
    }

    @Log("查询菜单")
    @ApiOperation("查询菜单")
    @GetMapping("/all")
    @PreAuthorize("@p.check('menu:list')")
    public R getMenus(MenuQueryCriteria criteria) {
        List<MenuDto> menuDtoList = entityService.queryAll(criteria);
        return R.ok(entityService.buildTree(menuDtoList));
    }

    /**
     * Description: 高级查询
     *
     * @param entity 含有高级查询条件
     * @return R
     */
    @Log("高级查询菜单")
    @ApiOperation(value = "多条件组合查询")
    @PostMapping("/all")
    @PreAuthorize("@p.check('menu:list')")
    public R advancedQuery(@RequestBody Menu entity) {
        List<Menu> entities = entityService.advancedQuery(entity);
        return R.ok(entityService.buildTree4Entites(entities));
    }

    @Log("新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping
    @PreAuthorize("@p.check('menu:add')")
    public R create(@Validated @RequestBody Menu resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return R.ok(entityService.create(resources));
    }

    @Log("修改菜单")
    @ApiOperation("修改菜单")
    @PutMapping
    @PreAuthorize("@p.check('menu:edit')")
    public R update(@Validated(Update.class) @RequestBody Menu resources) {
        entityService.update2(resources);
        return R.ok();
    }

    @Log("删除菜单")
    @ApiOperation("删除菜单")
    @DeleteMapping
    @PreAuthorize("@p.check('menu:del')")
    public R delete(@RequestBody Set<Long> ids) {
        Set<Menu> menuSet = new HashSet<>();
        for (Long id : ids) {
            List<Menu> menuList = entityService.findByPid(id);
            menuSet.add(entityService.findOne(id));
            menuSet = entityService.getDeleteMenus(menuList, menuSet);
        }
        entityService.delete(menuSet);
        return R.ok();
    }
}
