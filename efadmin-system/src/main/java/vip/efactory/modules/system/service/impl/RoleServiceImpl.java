package vip.efactory.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.EntityExistException;
import vip.efactory.modules.system.entity.Menu;
import vip.efactory.modules.system.entity.Role;
import vip.efactory.modules.system.repository.RoleRepository;
import vip.efactory.modules.system.service.RoleService;
import vip.efactory.modules.system.service.dto.CommonQueryCriteria;
import vip.efactory.modules.system.service.dto.RoleDTO;
import vip.efactory.modules.system.service.dto.RoleSmallDTO;
import vip.efactory.modules.system.service.mapper.RoleMapper;
import vip.efactory.modules.system.service.mapper.RoleSmallMapper;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<Role, Long, RoleRepository> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleSmallMapper roleSmallMapper;

    @Override
    public Object queryAll(Pageable pageable) {
        return roleMapper.toDto(br.findAll(pageable).getContent());
    }

    @Override
    public Object queryAll(CommonQueryCriteria criteria, Pageable pageable) {
        Page<Role> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(roleMapper::toDto));
    }

    @Override
    public RoleDTO findRoleDTOById(long id) {
        Optional<Role> role = br.findById(id);
        ValidationUtil.isNull(role, "Role", "id", id);
        return roleMapper.toDto(role.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO create(Role resources) {
        if (br.findByName(resources.getName()) != null) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }
        return roleMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role update(Role resources) {

        Optional<Role> optionalRole = br.findById(resources.getId());
        ValidationUtil.isNull(optionalRole, "Role", "id", resources.getId());

        Role role = optionalRole.get();

        Role role1 = br.findByName(resources.getName());

        if (role1 != null && !role1.getId().equals(role.getId())) {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }

        role.setName(resources.getName());
        role.setRemark(resources.getRemark());
        role.setDataScope(resources.getDataScope());
        role.setDepts(resources.getDepts());
        role.setLevel(resources.getLevel());
        br.save(role);
        return resources;
    }

    @Override
    public void updatePermission(Role resources, RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        role.setPermissions(resources.getPermissions());
        br.save(role);
    }

    @Override
    public void updateMenu(Role resources, RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        role.setMenus(resources.getMenus());
        br.save(role);
    }

    @Override
    public void untiedMenu(Menu menu) {
        Set<Role> roles = br.findByMenus_Id(menu.getId());
        for (Role role : roles) {
            menu.getRoles().remove(role);
            role.getMenus().remove(menu);
            br.save(role);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        br.deleteById(id);
    }

    @Override
    public List<RoleSmallDTO> findByUsers_Id(Long id) {
        return roleSmallMapper.toDto(br.findByUsers_Id(id).stream().collect(Collectors.toList()));
    }

    @Override
    public Integer findByRoles(Set<Role> roles) {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        for (Role role : roles) {
            roleDTOS.add(findRoleDTOById(role.getId()));
        }
        return Collections.min(roleDTOS.stream().map(RoleDTO::getLevel).collect(Collectors.toList()));
    }
}
