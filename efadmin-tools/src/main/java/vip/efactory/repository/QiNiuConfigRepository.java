package vip.efactory.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.domain.QiniuConfig;

public interface QiNiuConfigRepository extends BaseRepository<QiniuConfig, Long> {

    /**
     * 编辑类型
     * @param type
     */
    @Modifying
    @Query(value = "update QiniuConfig set type = ?1")
    void update(String type);
}
