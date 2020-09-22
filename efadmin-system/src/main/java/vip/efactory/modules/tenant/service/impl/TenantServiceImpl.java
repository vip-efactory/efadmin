package vip.efactory.modules.tenant.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.tenant.domain.Tenant;
import vip.efactory.modules.tenant.repository.TenantRepository;
import vip.efactory.modules.tenant.service.ITenantService;
import vip.efactory.modules.tenant.service.dto.TenantDto;
import vip.efactory.modules.tenant.service.dto.TenantQueryCriteria;
import vip.efactory.modules.tenant.service.mapper.TenantMapper;
import vip.efactory.utils.FileUtil;
import vip.efactory.utils.QueryHelp;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
 * 系统租户 服务层实现
 *
 * @author vip-efactory
 * @date 2020-04-11
 */
@Service
//@CacheConfig(cacheNames = "tenant")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@AllArgsConstructor
public class TenantServiceImpl extends BaseServiceImpl<Tenant, Long, TenantRepository> implements ITenantService {

    private final TenantMapper tenantMapper;

    @Override
    //@Cacheable
    public Page<TenantDto> queryAll(TenantQueryCriteria criteria, Pageable pageable) {
        Page<Tenant> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return page.map(tenantMapper::toDto);
    }
//
//    @Override
//    //@Cacheable
//    public List<TenantDto> queryAll(TenantQueryCriteria criteria){
//        return tenantMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
//    }
//
//    @Override
//    //@Cacheable(key = "#p0")
//    public TenantDto findDtoById(Long id) {
//        Tenant tenant = br.findById(id).orElseGet(Tenant::new);
//        ValidationUtil.isNull(tenant.getId(),"Tenant","id",id);
//        return tenantMapper.toDto(tenant);
//    }
//
//    @Override
//    //@CacheEvict(allEntries = true)
//    @Transactional(rollbackFor = Exception.class)
//    public TenantDto create(Tenant resources) {
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        return tenantMapper.toDto(br.save(resources));
//    }
//
//    @Override
//    //@CacheEvict(allEntries = true)
//    @Transactional(rollbackFor = Exception.class)
//    public void edit(Tenant resources) {
//        Tenant tenant = br.findById(resources.getId()).orElseGet(Tenant::new);
//        ValidationUtil.isNull( tenant.getId(),"Tenant","id",resources.getId());
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        tenant.copy(resources);
//        br.save(tenant);
//    }


    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            br.deleteById(id);
        }
    }

    @Override
    public List<Tenant> findAllByStatusEquals(Integer enable) {
        return br.findAllByStatusEquals(enable);
    }

    @Override
    public void download(List<TenantDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TenantDto tenant : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("租户名称", tenant.getTenantName());
            map.put("租户编码", tenant.getTenantCode());
            map.put("DB用户名", tenant.getDbUsername());
            map.put("DB密码", tenant.getDbPassword());
            map.put("驱动类名", tenant.getDriverClassName());
            map.put("JDBC链接", tenant.getJdbcUrl());
            map.put("租户状态,-1禁用；0正常等", tenant.getStatus());
            map.put("备注", tenant.getRemark());
            map.put("创建时间", tenant.getCreateTime().toString());
            map.put("创建人", tenant.getCreatorNum());
            map.put("更新时间", tenant.getUpdateTime().toString());
            map.put("更新人", tenant.getUpdaterNum());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
