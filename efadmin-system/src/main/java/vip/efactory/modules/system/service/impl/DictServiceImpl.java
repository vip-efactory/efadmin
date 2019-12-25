package vip.efactory.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.system.entity.Dict;
import vip.efactory.modules.system.repository.DictRepository;
import vip.efactory.modules.system.service.DictService;
import vip.efactory.modules.system.service.dto.DictDTO;
import vip.efactory.modules.system.service.mapper.DictMapper;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DictServiceImpl extends BaseServiceImpl<Dict, Long, DictRepository> implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public Object queryAll(DictDTO dict, Pageable pageable) {
        Page<Dict> page = br.findAll((root, query, cb) -> QueryHelp.getPredicate(root, dict, cb), pageable);
        return PageUtil.toPage(page.map(dictMapper::toDto));
    }

    @Override
    public DictDTO findDictDTOById(Long id) {
        Optional<Dict> dict = br.findById(id);
        ValidationUtil.isNull(dict, "Dict", "id", id);
        return dictMapper.toDto(dict.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDTO create(Dict resources) {
        return dictMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dict update(Dict resources) {
        Optional<Dict> optionalDict = br.findById(resources.getId());
        ValidationUtil.isNull(optionalDict, "Dict", "id", resources.getId());
        Dict dict = optionalDict.get();
        resources.setId(dict.getId());
        br.save(resources);
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        br.deleteById(id);
    }
}
