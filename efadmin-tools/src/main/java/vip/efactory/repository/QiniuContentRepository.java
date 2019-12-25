package vip.efactory.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.entity.QiniuContent;

public interface QiniuContentRepository extends BaseRepository<QiniuContent, Long>, JpaSpecificationExecutor {

    /**
     * 根据key查询
     *
     * @param key
     * @return
     */
    QiniuContent findByKey(String key);
}
