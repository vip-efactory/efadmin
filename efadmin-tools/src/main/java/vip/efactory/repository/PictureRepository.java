package vip.efactory.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vip.efactory.ejpa.base.repository.BaseRepository;
import vip.efactory.domain.Picture;

public interface PictureRepository extends BaseRepository<Picture, Long>, JpaSpecificationExecutor {

    /**
     * 根据 Mds 值查询文件
     * @param code 值
     * @return /
     */
    Picture findByMd5Code(String code);

    /**
     * 根据连接地址查询
     * @param url /
     * @return /
     */
    boolean existsByUrl(String url);
}
