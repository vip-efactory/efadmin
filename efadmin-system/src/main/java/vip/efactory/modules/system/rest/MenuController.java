package vip.efactory.modules.system.rest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.common.base.valid.Update;
import vip.efactory.ejpa.base.controller.BaseController;

import vip.efactory.exception.BadRequestException;
import vip.efactory.exception.EntityExistException;
import vip.efactory.modules.system.domain.Menu;
import vip.efactory.modules.system.service.MenuService;
import vip.efactory.modules.system.service.RoleService;
import vip.efactory.modules.system.service.UserService;
import vip.efactory.modules.system.service.dto.MenuDto;
import vip.efactory.modules.system.service.dto.MenuQueryCriteria;
import vip.efactory.modules.system.service.dto.UserDto;
import vip.efactory.utils.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@AllArgsConstructor
@Tag(name = "系统：菜单管理") // 替换@Api为@Tag
@RestController
@RequestMapping("/api/menus")
@SuppressWarnings(value = { "rawtypes", "unchecked" }) // 压制原生类型的警告
public class MenuController extends BaseController<Menu, MenuService, Long> {
    private static final String ENTITY_NAME = "menu";

    private final UserService userService;
    private final RoleService roleService;
    private final ObjectMapper objectMapper;

    @Log("导出菜单数据")
    @Operation(summary = "导出菜单数据") // 替换@ApiOperation
    @GetMapping(value = "/download")
    @PreAuthorize("@p.check('menu:list')")
    public void download(HttpServletResponse response, MenuQueryCriteria criteria) throws IOException {
        entityService.download(entityService.queryAll(criteria), response);
    }

    @Operation(summary = "获取前端所需菜单") // 替换@ApiOperation
    @GetMapping(value = "/build")
    public R buildMenus() {
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        List<MenuDto> menuDtoList = entityService.findByRoles(roleService.findByUsersId(user.getId()));
        List<MenuDto> menuDtos = (List<MenuDto>) entityService.buildTree(menuDtoList).get("content");
        return R.ok(entityService.buildMenus(menuDtos));
    }

    @Operation(summary = "返回全部的菜单") // 替换@ApiOperation
    @GetMapping(value = "/tree")
    @PreAuthorize("@p.check('menu:list','roles:list')")
    public R getMenuTree() {
        return R.ok(entityService.getMenuTree(entityService.findByPid(0L)));
    }

    @Log("查询菜单")
    @Operation(summary = "查询菜单") // 替换@ApiOperation
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
    @Override
    @Log("高级查询菜单")
    @Operation(summary = "多条件组合查询") // 替换@ApiOperation(value = "...")
    @PostMapping("/all")
    @PreAuthorize("@p.check('menu:list')")
    public R advancedQuery(@RequestBody Menu entity) {
        List<Menu> entities = entityService.advancedQuery(entity);
        return R.ok(entityService.buildTree4Entites(entities));
    }

    @Log("新增菜单")
    @Operation(summary = "新增菜单")
    @PostMapping
    @PreAuthorize("@p.check('menu:add')")
    public void create(@Validated @RequestBody Menu resources, HttpServletResponse response) throws IOException {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        try {
            entityService.create(resources);
            response.setStatus(200);
        } catch (BadRequestException | EntityExistException e) {
            Map<String, Object> errorMap = new HashMap<>(3);
            errorMap.put("status", 400);
            errorMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            errorMap.put("message", e.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(400);
            objectMapper.writeValue(response.getWriter(), errorMap);
        }
    }

    @Log("修改菜单")
    @Operation(summary = "修改菜单")
    @PutMapping
    @PreAuthorize("@p.check('menu:edit')")
// 用上面改后的方法签名
    public void update(@Validated(Update.class) @RequestBody Menu resources, HttpServletResponse response) throws IOException {
        try {
            entityService.update2(resources);
            // 成功时仅做这1行：设置200状态码，无任何返回内容（极简）
            response.setStatus(200);
        } catch (BadRequestException | EntityExistException e) {
            // 你原有的errorMap构造逻辑：1行不动，完全保留
            Map<String, Object> errorMap = new HashMap<>(3);
            errorMap.put("status", 400);
            errorMap.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            errorMap.put("message", e.getMessage());

            // 仅加这3行：原生写响应，绕开框架封装（无任何多余代码）
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(400);
            objectMapper.writeValue(response.getWriter(), errorMap);
        }
    }

    @Log("删除菜单")
    @Operation(summary = "删除菜单") // 替换@ApiOperation
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