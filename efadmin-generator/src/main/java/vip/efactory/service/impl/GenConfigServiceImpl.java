package vip.efactory.service.impl;

import org.springframework.stereotype.Service;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.entity.GenConfig;
import vip.efactory.repository.GenConfigRepository;
import vip.efactory.service.GenConfigService;

import java.util.Optional;

@Service
public class GenConfigServiceImpl extends BaseServiceImpl<GenConfig, Long, GenConfigRepository> implements GenConfigService {

    @Override
    public GenConfig find() {
        Optional<GenConfig> genConfig = br.findById(1L);
        if (genConfig.isPresent()) {
            return genConfig.get();
        } else {
            return new GenConfig();
        }
    }

    @Override
    public GenConfig update(GenConfig genConfig) {
        genConfig.setId(1L);
        return br.save(genConfig);
    }
}
