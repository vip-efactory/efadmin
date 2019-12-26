package vip.efactory.repository;

import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.domain.GenConfig;

public interface GenConfigRepository extends BaseRepository<GenConfig, Long> {

    /**
     * 查询表配置
     * @param tableName 表名
     * @return /
     */
    GenConfig findByTableName(String tableName);
}
