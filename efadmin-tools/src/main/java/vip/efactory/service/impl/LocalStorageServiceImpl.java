package vip.efactory.service.impl;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.domain.LocalStorage;
import vip.efactory.ejpa.base.controller.EPage;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;
import vip.efactory.exception.BadRequestException;
import vip.efactory.repository.LocalStorageRepository;
import vip.efactory.service.LocalStorageService;
import vip.efactory.service.dto.LocalStorageDto;
import vip.efactory.service.dto.LocalStorageQueryCriteria;
import vip.efactory.service.mapper.LocalStorageMapper;
import vip.efactory.utils.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "localStorage")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LocalStorageServiceImpl extends BaseServiceImpl<LocalStorage, Long, LocalStorageRepository> implements LocalStorageService {

    private final LocalStorageMapper localStorageMapper;

    @Value("${file.path}")
    private String path;

    @Value("${file.maxSize}")
    private long maxSize;

    public LocalStorageServiceImpl(LocalStorageMapper localStorageMapper) {
        this.localStorageMapper = localStorageMapper;
    }

    @Override
//    @Cacheable
    public Object queryAll(LocalStorageQueryCriteria criteria, Pageable pageable) {
        Page<LocalStorage> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return new EPage(page.map(localStorageMapper::toDto));
    }

    @Override
    @Cacheable
    public List<LocalStorageDto> queryAll(LocalStorageQueryCriteria criteria) {
        return localStorageMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public LocalStorageDto findDtoById(Long id) {
        LocalStorage localStorage = br.findById(id).orElseGet(LocalStorage::new);
        ValidationUtil.isNull(localStorage.getId(), "LocalStorage", "id", id);
        return localStorageMapper.toDto(localStorage);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public LocalStorageDto create(String name, MultipartFile multipartFile) {
        FileUtil.checkSize(maxSize, multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        String finalPath = path + TenantHolder.getTenantId() + File.separator;  // 加上租户信息，以便数据隔离
        File file = FileUtil.upload(multipartFile, finalPath + type + File.separator);
        if (ObjectUtil.isNull(file)) {
            throw new BadRequestException("上传失败");
        }
        try {
            name = StringUtils.isBlank(name) ? FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) : name;
            LocalStorage localStorage = new LocalStorage(
                    TenantHolder.getTenantId() + File.separator + type + File.separator + file.getName(),
                    name,
                    suffix,
                    file.getPath(),
                    type,
                    FileUtil.getSize(multipartFile.getSize()),
                    SecurityUtils.getUsername()
            );
            return localStorageMapper.toDto(br.save(localStorage));
        } catch (Exception e) {
            FileUtil.del(file);
            throw e;
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update2(LocalStorage resources) {
        LocalStorage localStorage = br.findById(resources.getId()).orElseGet(LocalStorage::new);
        ValidationUtil.isNull(localStorage.getId(), "LocalStorage", "id", resources.getId());
        localStorage.copy(resources);
        br.save(localStorage);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            LocalStorage storage = br.findById(id).orElseGet(LocalStorage::new);
            FileUtil.del(storage.getPath());
            br.delete(storage);
        }
    }

    @Override
    public void download(List<LocalStorageDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LocalStorageDto localStorageDTO : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("文件名", localStorageDTO.getRealName());
            map.put("备注名", localStorageDTO.getName());
            map.put("文件类型", localStorageDTO.getType());
            map.put("文件大小", localStorageDTO.getSize());
            map.put("操作人", localStorageDTO.getOperate());
            map.put("创建日期", localStorageDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
