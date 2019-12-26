package vip.efactory.service;

import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.domain.GenConfig;


public interface GenConfigService extends IBaseService<GenConfig, Long> {

    /**
     * 查询表配置
     * @param tableName 表名
     * @return 表配置
     */
    GenConfig find(String tableName);

    /**
     * 更新表配置
     * @param tableName 表名
     * @param genConfig 表配置
     * @return 表配置
     */
    GenConfig update(String tableName, GenConfig genConfig);
}
