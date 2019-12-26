package vip.efactory.modules.system.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.system.entity.DictDetail;
import vip.efactory.modules.system.repository.DictDetailRepository;
import vip.efactory.modules.system.service.DictDetailService;
import vip.efactory.modules.system.service.dto.DictDetailDto;
import vip.efactory.modules.system.service.dto.DictDetailQueryCriteria;
import vip.efactory.modules.system.service.mapper.DictDetailMapper;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.Map;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "dictDetail")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailServiceImpl extends BaseServiceImpl<DictDetail, Long, DictDetailRepository> implements DictDetailService {

    private final DictDetailMapper dictDetailMapper;

    public DictDetailServiceImpl(DictDetailMapper dictDetailMapper) {
        this.dictDetailMapper = dictDetailMapper;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
        Page<DictDetail> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    @Override
    @Cacheable(key = "#p0")
    public DictDetailDto findDtoById(Long id) {
        DictDetail dictDetail = br.findById(id).orElseGet(DictDetail::new);
        ValidationUtil.isNull(dictDetail.getId(),"DictDetail","id",id);
        return dictDetailMapper.toDto(dictDetail);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public DictDetailDto create(DictDetail resources) {
        return dictDetailMapper.toDto(br.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update2(DictDetail resources) {
        DictDetail dictDetail = br.findById(resources.getId()).orElseGet(DictDetail::new);
        ValidationUtil.isNull( dictDetail.getId(),"DictDetail","id",resources.getId());
        resources.setId(dictDetail.getId());
        br.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        br.deleteById(id);
    }
}
