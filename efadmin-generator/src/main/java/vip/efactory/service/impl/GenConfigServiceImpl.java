package vip.efactory.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.entity.GenConfig;
import vip.efactory.repository.GenConfigRepository;
import vip.efactory.service.GenConfigService;
import vip.efactory.utils.StringUtils;

import java.io.File;

@Service
@CacheConfig(cacheNames = "genConfig")
public class GenConfigServiceImpl extends BaseServiceImpl<GenConfig, Long, GenConfigRepository> implements GenConfigService {
    @Override
    @Cacheable(key = "#p0")
    public GenConfig find(String tableName) {
        GenConfig genConfig = br.findByTableName(tableName);
        if(genConfig == null){
            return new GenConfig(tableName);
        }
        return genConfig;
    }

    @Override
    @CachePut(key = "#p0")
    public GenConfig update(String tableName, GenConfig genConfig) {
        // 如果 api 路径为空，则自动生成路径
        if(StringUtils.isBlank(genConfig.getApiPath())){
            String separator = File.separator;
            String[] paths;
            String symbol = "\\";
            if (symbol.equals(separator)) {
                paths = genConfig.getPath().split("\\\\");
            } else {
                paths = genConfig.getPath().split(File.separator);
            }
            StringBuilder api = new StringBuilder();
            for (String path : paths) {
                api.append(path);
                api.append(separator);
                if ("src".equals(path)) {
                    api.append("api");
                    break;
                }
            }
            genConfig.setApiPath(api.toString());
        }
        return br.save(genConfig);
    }
}
