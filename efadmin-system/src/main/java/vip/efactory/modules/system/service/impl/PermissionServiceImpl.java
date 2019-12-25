package vip.efactory.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.BadRequestException;
import vip.efactory.exception.EntityExistException;
import vip.efactory.modules.system.entity.Permission;
import vip.efactory.modules.system.repository.PermissionRepository;
import vip.efactory.modules.system.service.PermissionService;
import vip.efactory.modules.system.service.dto.CommonQueryCriteria;
import vip.efactory.modules.system.service.dto.PermissionDTO;
import vip.efactory.modules.system.service.mapper.PermissionMapper;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.*;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long, PermissionRepository> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionDTO> queryAll(CommonQueryCriteria criteria) {
        return permissionMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public PermissionDTO findById(long id) {
        Optional<Permission> permission = br.findById(id);
        ValidationUtil.isNull(permission, "Permission", "id", id);
        return permissionMapper.toDto(permission.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PermissionDTO create(Permission resources) {
        if (br.findByName(resources.getName()) != null) {
            throw new EntityExistException(Permission.class, "name", resources.getName());
        }
        return permissionMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Permission update(Permission resources) {
        Optional<Permission> optionalPermission = br.findById(resources.getId());
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        ValidationUtil.isNull(optionalPermission, "Permission", "id", resources.getId());

        Permission permission = optionalPermission.get();

        Permission permission1 = br.findByName(resources.getName());

        if (permission1 != null && !permission1.getId().equals(permission.getId())) {
            throw new EntityExistException(Permission.class, "name", resources.getName());
        }

        permission.setName(resources.getName());
        permission.setAlias(resources.getAlias());
        permission.setPid(resources.getPid());
        br.save(permission);
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        List<Permission> permissionList = br.findByPid(id);
        for (Permission permission : permissionList) {
            br.delete(permission);
        }
        br.deleteById(id);
    }

    @Override
    public Object getPermissionTree(List<Permission> permissions) {
        List<Map<String, Object>> list = new LinkedList<>();
        permissions.forEach(permission -> {
                    if (permission != null) {
                        List<Permission> permissionList = br.findByPid(permission.getId());
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", permission.getId());
                        map.put("label", permission.getAlias());
                        if (permissionList != null && permissionList.size() != 0) {
                            map.put("children", getPermissionTree(permissionList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<Permission> findByPid(long pid) {
        return br.findByPid(pid);
    }

    @Override
    public Object buildTree(List<PermissionDTO> permissionDTOS) {

        List<PermissionDTO> trees = new ArrayList<PermissionDTO>();

        for (PermissionDTO permissionDTO : permissionDTOS) {

            if ("0".equals(permissionDTO.getPid().toString())) {
                trees.add(permissionDTO);
            }

            for (PermissionDTO it : permissionDTOS) {
                if (it.getPid().equals(permissionDTO.getId())) {
                    if (permissionDTO.getChildren() == null) {
                        permissionDTO.setChildren(new ArrayList<PermissionDTO>());
                    }
                    permissionDTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = permissionDTOS != null ? permissionDTOS.size() : 0;

        Map map = new HashMap();
        map.put("content", trees.size() == 0 ? permissionDTOS : trees);
        map.put("totalElements", totalElements);
        return map;
    }
}
