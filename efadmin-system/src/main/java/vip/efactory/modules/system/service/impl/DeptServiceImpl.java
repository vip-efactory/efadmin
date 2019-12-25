package vip.efactory.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.system.entity.Dept;
import vip.efactory.modules.system.repository.DeptRepository;
import vip.efactory.modules.system.service.DeptService;
import vip.efactory.modules.system.service.dto.DeptDTO;
import vip.efactory.modules.system.service.dto.DeptQueryCriteria;
import vip.efactory.modules.system.service.mapper.DeptMapper;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl extends BaseServiceImpl<Dept, Long, DeptRepository> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<DeptDTO> queryAll(DeptQueryCriteria criteria) {
        return deptMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public DeptDTO findDeptDTOById(Long id) {
        Optional<Dept> dept = br.findById(id);
        ValidationUtil.isNull(dept, "Dept", "id", id);
        return deptMapper.toDto(dept.get());
    }

    @Override
    public List<Dept> findByPid(long pid) {
        return br.findByPid(pid);
    }

    @Override
    public Set<Dept> findByRoleIds(Long id) {
        return br.findByRoles_Id(id);
    }

    @Override
    public Object buildTree(List<DeptDTO> deptDTOS) {
        Set<DeptDTO> trees = new LinkedHashSet<>();
        Set<DeptDTO> depts = new LinkedHashSet<>();
        List<String> deptNames = deptDTOS.stream().map(DeptDTO::getName).collect(Collectors.toList());
        Boolean isChild;
        for (DeptDTO deptDTO : deptDTOS) {
            isChild = false;
            if ("0".equals(deptDTO.getPid().toString())) {
                trees.add(deptDTO);
            }
            for (DeptDTO it : deptDTOS) {
                if (it.getPid().equals(deptDTO.getId())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<DeptDTO>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if (isChild)
                depts.add(deptDTO);
            else if (!deptNames.contains(br.findNameById(deptDTO.getPid())))
                depts.add(deptDTO);
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = deptDTOS != null ? deptDTOS.size() : 0;

        Map map = new HashMap();
        map.put("totalElements", totalElements);
        map.put("content", CollectionUtils.isEmpty(trees) ? deptDTOS : trees);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeptDTO create(Dept resources) {
        return deptMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dept update(Dept resources) {
        if (resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        Optional<Dept> optionalDept = br.findById(resources.getId());
        ValidationUtil.isNull(optionalDept, "Dept", "id", resources.getId());
        Dept dept = optionalDept.get();
        resources.setId(dept.getId());
        br.save(resources);

        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        br.deleteById(id);
    }
}
