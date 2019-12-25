package vip.efactory.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.system.entity.DictDetail;
import vip.efactory.modules.system.repository.DictDetailRepository;
import vip.efactory.modules.system.service.DictDetailService;
import vip.efactory.modules.system.service.dto.DictDetailDTO;
import vip.efactory.modules.system.service.dto.DictDetailQueryCriteria;
import vip.efactory.modules.system.service.mapper.DictDetailMapper;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictDetailServiceImpl extends BaseServiceImpl<DictDetail, Long, DictDetailRepository> implements DictDetailService {

    @Autowired
    private DictDetailMapper dictDetailMapper;

    @Override
    public Map queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
        Page<DictDetail> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    @Override
    public DictDetailDTO findDictDeatilDTOById(Long id) {
        Optional<DictDetail> dictDetail = br.findById(id);
        ValidationUtil.isNull(dictDetail, "DictDetail", "id", id);
        return dictDetailMapper.toDto(dictDetail.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDetailDTO create(DictDetail resources) {
        return dictDetailMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDetail update(DictDetail resources) {
        Optional<DictDetail> optionalDictDetail = br.findById(resources.getId());
        ValidationUtil.isNull(optionalDictDetail, "DictDetail", "id", resources.getId());
        DictDetail dictDetail = optionalDictDetail.get();
        resources.setId(dictDetail.getId());
        br.save(resources);
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        br.deleteById(id);
    }
}
