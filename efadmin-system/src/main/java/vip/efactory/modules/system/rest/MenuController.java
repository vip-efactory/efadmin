package vip.efactory.modules.system.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.aop.log.Log;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.entity.Menu;
import vip.efactory.modules.system.service.MenuService;
import vip.efactory.modules.system.service.RoleService;
import vip.efactory.modules.system.service.UserService;
import vip.efactory.modules.system.service.dto.CommonQueryCriteria;
import vip.efactory.modules.system.service.dto.MenuDTO;
import vip.efactory.modules.system.service.dto.UserDTO;
import vip.efactory.utils.SecurityUtils;

import java.util.List;

@RestController
@RequestMapping("api")
public class MenuController extends BaseController<Menu, MenuService> {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private static final String ENTITY_NAME = "menu";

    /**
     * 构建前端路由所需要的菜单
     *
     * @return
     */
    @GetMapping(value = "/menus/build")
    public ResponseEntity buildMenus() {
        UserDTO user = userService.findByName(SecurityUtils.getUsername());
        List<MenuDTO> menuDTOList = entityService.findByRoles(roleService.findByUsers_Id(user.getId()));
        List<MenuDTO> menuDTOTree = (List<MenuDTO>) entityService.buildTree(menuDTOList).get("content");
        return new ResponseEntity(entityService.buildMenus(menuDTOTree), HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     *
     * @return
     */
    @GetMapping(value = "/menus/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE','MENU_EDIT','ROLES_SELECT','ROLES_ALL')")
    public ResponseEntity getMenuTree() {
        return new ResponseEntity(entityService.getMenuTree(entityService.findByPid(0L)), HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenus(CommonQueryCriteria criteria) {
        List<MenuDTO> menuDTOList = entityService.queryAll(criteria);
        return new ResponseEntity(entityService.buildTree(menuDTOList), HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE')")
    public ResponseEntity create(@Validated @RequestBody Menu resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(entityService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_EDIT')")
    public ResponseEntity update(@Validated(Menu.Update.class) @RequestBody Menu resources) {
        entityService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping(value = "/menus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        List<Menu> menuList = entityService.findByPid(id);

        // 特殊情况，对级联删除进行处理
        for (Menu menu : menuList) {
            roleService.untiedMenu(menu);
            entityService.delete(menu.getId());
        }
        roleService.untiedMenu(entityService.findOne(id));
        entityService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
