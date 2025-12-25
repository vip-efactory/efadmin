package vip.efactory.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.common.base.page.EPage;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.mnt.domain.Database;
import vip.efactory.modules.mnt.repository.DatabaseRepository;
import vip.efactory.modules.mnt.service.DatabaseService;
import vip.efactory.modules.mnt.service.dto.DatabaseDto;
import vip.efactory.modules.mnt.service.dto.DatabaseQueryCriteria;
import vip.efactory.modules.mnt.service.mapper.DatabaseMapper;
import vip.efactory.modules.mnt.util.SqlUtils;
import vip.efactory.utils.FileUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;


import java.io.IOException;
import java.util.*;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "database")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DatabaseServiceImpl extends BaseServiceImpl<Database, String, DatabaseRepository> implements DatabaseService {

    private DatabaseMapper databaseMapper;

    public DatabaseServiceImpl(DatabaseMapper databaseMapper) {
        this.databaseMapper = databaseMapper;
    }

    @Override
    @Cacheable
    public Object queryAll(DatabaseQueryCriteria criteria, Pageable pageable) {
        Page<Database> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> {
            // 基础条件：走注解 @Query 生成
            var base = QueryHelp.getPredicate(root, criteria, criteriaBuilder);

            // 追加 blurry 多字段模糊
            if (criteria != null && criteria.getBlurry() != null && !criteria.getBlurry().isEmpty()) {
                String like = "%" + criteria.getBlurry().trim() + "%";
                // 按实体字段名补充需要参与模糊的列
                var pName = criteriaBuilder.like(root.get("name"), like);
                var pUrl  = criteriaBuilder.like(root.get("jdbcUrl"), like);

                var blurryOr = criteriaBuilder.or(pName, pUrl);
                return base != null ? criteriaBuilder.and(base, blurryOr) : blurryOr;
            }
            return base;
        }, pageable);

        return new EPage(page.map(databaseMapper::toDto));
    }

    @Override
    @Cacheable
    public List<DatabaseDto> queryAll(DatabaseQueryCriteria criteria) {
        return databaseMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Cacheable
    public DatabaseDto findDtoById(String id) {
        Database database = br.findById(id).orElseGet(Database::new);
        ValidationUtil.isNull(database.getId(), "Database", "id", id);
        return databaseMapper.toDto(database);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public DatabaseDto create(Database resources) {
        resources.setId(IdUtil.simpleUUID());
        return databaseMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void update2(Database resources) {
        Database database = br.findById(resources.getId()).orElseGet(Database::new);
        ValidationUtil.isNull(database.getId(), "Database", "id", resources.getId());
        database.copy(resources);
        br.save(database);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void delete(Set<String> ids) {
        for (String id : ids) {
            br.deleteById(id);
        }
    }

    @Override
    public boolean testConnection(Database resources) {
        try {
            return SqlUtils.testConnection(resources.getJdbcUrl(), resources.getUserName(), resources.getPwd());
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public void download(List<DatabaseDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DatabaseDto databaseDto : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("数据库名称", databaseDto.getName());
            map.put("数据库连接地址", databaseDto.getJdbcUrl());
            map.put("用户名", databaseDto.getUserName());
            map.put("创建日期", databaseDto.getCreateTime().toString());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
