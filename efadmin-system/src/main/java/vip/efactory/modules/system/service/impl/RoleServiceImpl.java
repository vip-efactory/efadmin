package vip.efactory.modules.system.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.common.base.page.EPage;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.EntityExistException;
import vip.efactory.modules.system.domain.Menu;
import vip.efactory.modules.system.domain.Role;
import vip.efactory.modules.system.repository.RoleRepository;
import vip.efactory.modules.system.service.RoleService;
import vip.efactory.modules.system.service.dto.RoleDto;
import vip.efactory.modules.system.service.dto.RoleQueryCriteria;
import vip.efactory.modules.system.service.dto.RoleSmallDto;
import vip.efactory.modules.system.service.dto.UserDto;
import vip.efactory.modules.system.service.mapper.RoleMapper;
import vip.efactory.modules.system.service.mapper.RoleSmallMapper;
import vip.efactory.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@CacheConfig(cacheNames = "role")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<Role, Long, RoleRepository> implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleSmallMapper roleSmallMapper;

    public RoleServiceImpl(RoleMapper roleMapper, RoleSmallMapper roleSmallMapper) {
        this.roleMapper = roleMapper;
        this.roleSmallMapper = roleSmallMapper;
    }

    @Override
    @Cacheable
    public Object queryAll(Pageable pageable) {
        return roleMapper.toDto(br.findAll(pageable).getContent());
    }

    @Override
    @Cacheable
    public List<RoleDto> queryAll(RoleQueryCriteria criteria) {
        return roleMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable
    public Object queryAll(RoleQueryCriteria criteria, Pageable pageable) {
        Page<Role> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return new EPage(page.map(roleMapper::toDto));
    }

    @Override
    @Cacheable(key = "#p0")
    public RoleDto findDtoById(long id) {
        Role role = br.findById(id).orElseGet(Role::new);
        ValidationUtil.isNull(role.getId(),"Role","id",id);
        return roleMapper.toDto(role);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public RoleDto create(Role resources) {
        if(br.findByName(resources.getName()) != null){
            throw new EntityExistException(Role.class,"username",resources.getName());
        }
        return roleMapper.toDto(br.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update2(Role resources) {
        Role role = br.findById(resources.getId()).orElseGet(Role::new);
        ValidationUtil.isNull(role.getId(),"Role","id",resources.getId());

        Role role1 = br.findByName(resources.getName());

        if(role1 != null && !role1.getId().equals(role.getId())){
            throw new EntityExistException(Role.class,"username",resources.getName());
        }
        role1 = br.findByPermission(resources.getPermission());
        if(role1 != null && !role1.getId().equals(role.getId())){
            throw new EntityExistException(Role.class,"permission",resources.getPermission());
        }
        role.setName(resources.getName());
        role.setRemark(resources.getRemark());
        role.setDataScope(resources.getDataScope());
        role.setDepts(resources.getDepts());
        role.setLevel(resources.getLevel());
        role.setPermission(resources.getPermission());
        br.save(role);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void updateMenu(Role resources, RoleDto roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        role.setMenus(resources.getMenus());
        br.save(role);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void untiedMenu(Long id) {
        br.untiedMenu(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            br.deleteById(id);
        }
    }

    @Override
    @Cacheable(key = "'findByUsers_Id:' + #p0")
    public List<RoleSmallDto> findByUsersId(Long id) {
        return roleSmallMapper.toDto(new ArrayList<>(br.findByUsers_Id(id)));
    }

    @Override
    @Cacheable
    public Integer findByRoles(Set<Role> roles) {
        Set<RoleDto> roleDtos = new HashSet<>();
        for (Role role : roles) {
            roleDtos.add(findDtoById(role.getId()));
        }
        return Collections.min(roleDtos.stream().map(RoleDto::getLevel).collect(Collectors.toList()));
    }

    @Override
    @Cacheable(key = "'loadPermissionByUser:' + #p0.username")
    public Collection<GrantedAuthority> mapToGrantedAuthorities(UserDto user) {
        Set<Role> roles = br.findByUsers_Id(user.getId());
        Set<String> permissions = roles.stream().filter(role -> isNotBlank(role.getPermission())).map(Role::getPermission).collect(Collectors.toSet());
        permissions.addAll(
                roles.stream().flatMap(role -> role.getMenus().stream())
                        .filter(menu -> isNotBlank(menu.getPermission()))
                        .map(Menu::getPermission).collect(Collectors.toSet())
        );
        return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public void download(List<RoleDto> roles, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RoleDto role : roles) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("角色名称", role.getName());
            map.put("默认权限", role.getPermission());
            map.put("角色级别", role.getLevel());
            map.put("描述", role.getRemark());
            map.put("创建日期", role.getCreateTime().toString());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
