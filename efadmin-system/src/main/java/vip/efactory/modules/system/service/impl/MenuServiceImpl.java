package vip.efactory.modules.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.BadRequestException;
import vip.efactory.exception.EntityExistException;
import vip.efactory.modules.system.domain.Menu;
import vip.efactory.modules.system.domain.vo.MenuMetaVo;
import vip.efactory.modules.system.domain.vo.MenuVo;
import vip.efactory.modules.system.repository.MenuRepository;
import vip.efactory.modules.system.service.MenuService;
import vip.efactory.modules.system.service.RoleService;
import vip.efactory.modules.system.service.dto.MenuDto;
import vip.efactory.modules.system.service.dto.MenuQueryCriteria;
import vip.efactory.modules.system.service.dto.RoleSmallDto;
import vip.efactory.modules.system.service.mapper.MenuMapper;
import vip.efactory.utils.FileUtil;
import vip.efactory.utils.MenuI18nUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static cn.hutool.core.text.CharSequenceUtil.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@CacheConfig(cacheNames = "menu")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long, MenuRepository> implements MenuService {

    private final MenuMapper menuMapper;

    private final RoleService roleService;

    public MenuServiceImpl(MenuMapper menuMapper, RoleService roleService) {
        this.menuMapper = menuMapper;
        this.roleService = roleService;
    }

    @Override
    @Cacheable
    public List<MenuDto> queryAll(MenuQueryCriteria criteria) {
//        Sort sort = new Sort(Sort.Direction.DESC,"id");
        return menuMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public MenuDto findDtoById(long id) {
        Menu menu = br.findById(id).orElseGet(Menu::new);
        ValidationUtil.isNull(menu.getId(), "Menu", "id", id);
        return menuMapper.toDto(menu);
    }

    @Override
    public List<MenuDto> findByRoles(List<RoleSmallDto> roles) {
        // 测试生成菜单的国际化文件
//        Entityi18nUtil.copyToLocale(MenuI18nUtil.geni18nPropertiesFile("messages_ui", br.findAll()));

        Set<Long> roleIds = roles.stream().map(RoleSmallDto::getId).collect(Collectors.toSet());
        LinkedHashSet<Menu> menus = br.findByRoles_IdInAndTypeNotOrderBySortAsc(roleIds, 2);
        // 如果不是中文简体环境(zh_CN),就进行国际化处理
        if (!LocaleContextHolder.getLocale().equals(Locale.SIMPLIFIED_CHINESE)) {
            MenuI18nUtil.handleLocale(menus);
        }
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(allEntries = true)
    public MenuDto create(Menu resources) {
        if (br.findByName(resources.getName()) != null) {
            throw new EntityExistException(Menu.class, "name", resources.getName());
        }
        if (isNotBlank(resources.getComponentName())) {
            if (br.findByComponentName(resources.getComponentName()) != null) {
                throw new EntityExistException(Menu.class, "componentName", resources.getComponentName());
            }
        }
        if (resources.getIFrame()) {
            String http = "http://", https = "https://";
            if (!(resources.getPath().toLowerCase().startsWith(http) || resources.getPath().toLowerCase().startsWith(https))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        return menuMapper.toDto(br.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    public void update2(Menu resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Menu menu = br.findById(resources.getId()).orElseGet(Menu::new);
        ValidationUtil.isNull(menu.getId(), "Permission", "id", resources.getId());

        if (resources.getIFrame()) {
            String http = "http://", https = "https://";
            if (!(resources.getPath().toLowerCase().startsWith(http) || resources.getPath().toLowerCase().startsWith(https))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        Menu menu1 = br.findByName(resources.getName());

        if (menu1 != null && !menu1.getId().equals(menu.getId())) {
            throw new EntityExistException(Menu.class, "name", resources.getName());
        }

        if (isNotBlank(resources.getComponentName())) {
            menu1 = br.findByComponentName(resources.getComponentName());
            if (menu1 != null && !menu1.getId().equals(menu.getId())) {
                throw new EntityExistException(Menu.class, "componentName", resources.getComponentName());
            }
        }
        menu.setName(resources.getName());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setPid(resources.getPid());
        menu.setSort(resources.getSort());
        menu.setCache(resources.getCache());
        menu.setHidden(resources.getHidden());
        menu.setComponentName(resources.getComponentName());
        menu.setPermission(resources.getPermission());
        menu.setType(resources.getType());
        br.save(menu);
    }

    @Override
    public Set<Menu> getDeleteMenus(List<Menu> menuList, Set<Menu> menuSet) {
        // 递归找出待删除的菜单
        for (Menu menu1 : menuList) {
            menuSet.add(menu1);
            List<Menu> menus = br.findByPid(menu1.getId());
            if (menus != null && menus.size() != 0) {
                getDeleteMenus(menus, menuSet);
            }
        }
        return menuSet;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Menu> menuSet) {
        for (Menu menu : menuSet) {
            roleService.untiedMenu(menu.getId());
            br.deleteById(menu.getId());
        }
    }

    @Override
//    @Cacheable(key = "'tree'")   //缓存会导致菜单与环境不一致
    public Object getMenuTree(List<Menu> menus) {
        // ========== 第一步：初始化所有key→中文的映射表（覆盖你返回的所有label） ==========
        Map<String, String> labelMap = new HashMap<>();
        // 系统管理模块
        labelMap.put("Menu.0.system.manage", "系统管理");
        labelMap.put("Menu.1.user.manage", "用户管理");
        labelMap.put("Menu.2.user.add", "新增用户");
        labelMap.put("Menu.2.user.edit", "编辑用户");
        labelMap.put("Menu.2.user.delete", "删除用户");
        // 角色管理
        labelMap.put("Menu.1.role.manage", "角色管理");
        labelMap.put("Menu.2.role.add", "新增角色");
        labelMap.put("Menu.2.role.edit", "编辑角色");
        labelMap.put("Menu.2.role.delete", "删除角色");
        // 菜单管理
        labelMap.put("Menu.1.menu.manage", "菜单管理");
        labelMap.put("Menu.2.menu.add", "新增菜单");
        labelMap.put("Menu.2.menu.edit", "编辑菜单");
        labelMap.put("Menu.2.menu.delete", "删除菜单");
        // 部门管理
        labelMap.put("Menu.1.dept.manage", "部门管理");
        labelMap.put("Menu.2.dept.add", "新增部门");
        labelMap.put("Menu.2.dept.edit", "编辑部门");
        labelMap.put("Menu.2.dept.delete", "删除部门");
        // 岗位管理
        labelMap.put("Menu.1.job.manage", "岗位管理");
        labelMap.put("Menu.2.job.add", "新增岗位");
        labelMap.put("Menu.2.job.edit", "编辑岗位");
        labelMap.put("Menu.2.job.delete", "删除岗位");
        // 字典管理
        labelMap.put("Menu.1.dict.manage", "字典管理");
        labelMap.put("Menu.2.dict.add", "新增字典");
        labelMap.put("Menu.2.dict.edit", "编辑字典");
        labelMap.put("Menu.2.dict.delete", "删除字典");
        // 员工管理
        labelMap.put("Menu.1.employee.manage", "员工管理");
        labelMap.put("Menu.2.employee.add", "新增员工");
        labelMap.put("Menu.2.employee.edit", "编辑员工");
        labelMap.put("Menu.2.employee.delete", "删除员工");
        // Github链接
        labelMap.put("Menu.1.github.link", "Github源码");
        // 系统监控
        labelMap.put("Menu.0.system.monitor", "系统监控");
        labelMap.put("Menu.1.operation.log", "操作日志");
        labelMap.put("Menu.1.sql.monitor", "SQL监控");
        labelMap.put("Menu.1.error.log", "异常日志");
        labelMap.put("Menu.1.online.user", "在线用户");
        labelMap.put("Menu.1.service.monitor", "服务监控");
        // 组件管理
        labelMap.put("Menu.0.component.manage", "组件管理");
        labelMap.put("Menu.1.icon.library", "图标库");
        labelMap.put("Menu.1.rich.text", "富文本");
        labelMap.put("Menu.1.markdown.editor", "Markdown编辑器");
        labelMap.put("Menu.1.yaml.editor", "Yaml编辑器");
        labelMap.put("Menu.1.echarts.library", "图表库");
        labelMap.put("Menu.1.ueditor.text", "Ueditor编辑器");
        // 多级菜单
        labelMap.put("Menu.0.multi.menu", "多级菜单");
        labelMap.put("Menu.1.second.menu1", "二级菜单1");
        labelMap.put("Menu.1.second.menu2", "二级菜单2");
        labelMap.put("Menu.1.third.menu1", "三级菜单1");
        labelMap.put("Menu.1.third.menu2", "三级菜单2");
        // 系统工具
        labelMap.put("Menu.0.system.tool", "系统工具");
        labelMap.put("Menu.1.email.tool", "邮件工具");
        labelMap.put("Menu.1.picture.manage", "图片管理");
        labelMap.put("Menu.2.picture.add", "上传图片");
        labelMap.put("Menu.2.picture.delete", "删除图片");
        labelMap.put("Menu.1.storage.manage", "存储管理");
        labelMap.put("Menu.2.storage.add", "上传文件");
        labelMap.put("Menu.2.storage.edit", "编辑文件");
        labelMap.put("Menu.2.storage.delete", "删除文件");
        labelMap.put("Menu.1.alipay.tool", "支付宝工具");
        labelMap.put("Menu.1.timed.task", "定时任务");
        labelMap.put("Menu.2.task.add", "新增任务");
        labelMap.put("Menu.2.task.edit", "编辑任务");
        labelMap.put("Menu.2.task.delete", "删除任务");
        labelMap.put("Menu.1.code.generator", "代码生成器");
        labelMap.put("Menu.1.api.docs", "接口文档");
        labelMap.put("Menu.1.generate.config", "生成配置");
        labelMap.put("Menu.1.generate.preview", "生成预览");
        // 运维管理
        labelMap.put("Menu.1.maintenance.manage", "运维管理");
        labelMap.put("Menu.1.server", "服务器管理");
        labelMap.put("Menu.2.server.add", "新增服务器");
        labelMap.put("Menu.2.server.edit", "编辑服务器");
        labelMap.put("Menu.2.server.delete", "删除服务器");
        labelMap.put("Menu.1.app.manage", "应用管理");
        labelMap.put("Menu.2.app.add", "新增应用");
        labelMap.put("Menu.2.app.edit", "编辑应用");
        labelMap.put("Menu.2.app.delete", "删除应用");
        labelMap.put("Menu.1.deploy.manage", "部署管理");
        labelMap.put("Menu.2.deploy.add", "新增部署");
        labelMap.put("Menu.2.deploy.edit", "编辑部署");
        labelMap.put("Menu.2.deploy.delete", "删除部署");
        labelMap.put("Menu.1.deploy.backup", "部署备份");
        labelMap.put("Menu.2.deployHistory.delete", "删除备份");
        labelMap.put("Menu.1.db.manage", "数据库管理");
        labelMap.put("Menu.2.db.add", "新增数据库");
        labelMap.put("Menu.2.db.edit", "编辑数据库");
        labelMap.put("Menu.2.db.delete", "删除数据库");

        // ========== 第二步：构建菜单树（原有逻辑仅替换label赋值） ==========
        List<Map<String, Object>> list = new LinkedList<>();
        // 遍历所有菜单，递归构建子菜单
        for (Menu menu : menus) {
            if (menu == null) {
                continue;
            }
            Map<String, Object> map = new HashMap<>(16);
            map.put("id", menu.getId());
            // 核心：从映射表取中文，无匹配则保留原key（不会null）
            map.put("label", labelMap.getOrDefault(menu.getName(), menu.getName()));
            // 递归处理子菜单
            List<Menu> childMenus = br.findByPid(menu.getId());
            if (!CollectionUtils.isEmpty(childMenus)) {
                map.put("children", getMenuTree(childMenus));
            }
            list.add(map);
        }
        return list;
    }

    @Override
    @Cacheable(key = "'pid:'+#p0")
    public List<Menu> findByPid(long pid) {
        return br.findByPid(pid);
    }

    @Override
    public Map<String, Object> buildTree(List<MenuDto> menuDtos) {
        List<MenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDto menuDTO : menuDtos) {
            if (menuDTO.getPid() == 0) {
                trees.add(menuDTO);
            }
            for (MenuDto it : menuDtos) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        Map<String, Object> map = new HashMap<>(2);
        if (trees.size() == 0) {
            trees = menuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        map.put("content", trees);
        map.put("totalElements", menuDtos.size());
        return map;
    }

    @Override
    public Map<String, Object> buildTree4Entites(List<Menu> menus) {
        if (!CollectionUtils.isEmpty(menus)) {
            List<MenuDto> trees = new ArrayList<>();
            menus.forEach(menu -> {
                MenuDto dto = menuMapper.toDto(menu); // 转换为DTO对象
                trees.add(dto);
            });
            return buildTree(trees);
        }
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    @Override
    public List<MenuVo> buildMenus(List<MenuDto> menuDtos) {
        List<MenuVo> list = new LinkedList<>();
        menuDtos.forEach(menuDTO -> {
                    if (menuDTO != null) {
                        List<MenuDto> menuDtoList = menuDTO.getChildren();
                        MenuVo menuVo = new MenuVo();
                        menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName()) ? menuDTO.getComponentName() : menuDTO.getName());
                        // 一级目录需要加斜杠，不然会报警告
                        menuVo.setPath(menuDTO.getPid() == 0 ? "/" + menuDTO.getPath() : menuDTO.getPath());
                        menuVo.setHidden(menuDTO.getHidden());
                        // 如果不是外链
                        if (!menuDTO.getIFrame()) {
                            if (menuDTO.getPid() == 0) {
                                menuVo.setComponent(isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                            } else if (!isEmpty(menuDTO.getComponent())) {
                                menuVo.setComponent(menuDTO.getComponent());
                            }
                        }
                        menuVo.setMeta(new MenuMetaVo(menuDTO.getName(), menuDTO.getIcon(), !menuDTO.getCache()));
                        if (menuDtoList != null && menuDtoList.size() != 0) {
                            menuVo.setAlwaysShow(true);
                            menuVo.setRedirect("noredirect");
                            menuVo.setChildren(buildMenus(menuDtoList));
                            // 处理是一级菜单并且没有子菜单的情况
                        } else if (menuDTO.getPid() == 0) {
                            MenuVo menuVo1 = new MenuVo();
                            menuVo1.setMeta(menuVo.getMeta());
                            // 非外链
                            if (!menuDTO.getIFrame()) {
                                menuVo1.setPath("index");
                                menuVo1.setName(menuVo.getName());
                                menuVo1.setComponent(menuVo.getComponent());
                            } else {
                                menuVo1.setPath(menuDTO.getPath());
                            }
                            menuVo.setName(null);
                            menuVo.setMeta(null);
                            menuVo.setComponent("Layout");
                            List<MenuVo> list1 = new ArrayList<>();
                            list1.add(menuVo1);
                            menuVo.setChildren(list1);
                        }
                        list.add(menuVo);
                    }
                }
        );
        return list;
    }

    @Override
    public Menu findOne(Long id) {
        Menu menu = br.findById(id).orElseGet(Menu::new);
        ValidationUtil.isNull(menu.getId(), "Menu", "id", id);
        return menu;
    }

    @Override
    public void download(List<MenuDto> menuDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MenuDto menuDTO : menuDtos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("菜单名称", menuDTO.getName());
            map.put("菜单类型", menuDTO.getType() == 0 ? "目录" : menuDTO.getType() == 1 ? "菜单" : "按钮");
            map.put("权限标识", menuDTO.getPermission());
            map.put("外链菜单", menuDTO.getIFrame() ? "是" : "否");
            map.put("菜单可见", menuDTO.getHidden() ? "否" : "是");
            map.put("是否缓存", menuDTO.getCache() ? "是" : "否");
            map.put("创建日期", menuDTO.getCreateTime().toString());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
