package vip.efactory.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.entity.QiniuConfig;
import vip.efactory.entity.QiniuContent;
import vip.efactory.service.dto.QiniuQueryCriteria;

@CacheConfig(cacheNames = "qiNiu")
public interface QiNiuService extends IBaseService<QiniuConfig, Long> {

    /**
     * 查询文件
     *
     * @param criteria
     * @param pageable
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(QiniuQueryCriteria criteria, Pageable pageable);

    /**
     * 查配置
     *
     * @return
     */
    @Cacheable(cacheNames = "qiNiuConfig", key = "'1'")
    QiniuConfig find();

    /**
     * 修改配置
     *
     * @param qiniuConfig
     * @return
     */
    @CachePut(cacheNames = "qiNiuConfig", key = "'1'")
    QiniuConfig update(QiniuConfig qiniuConfig);

    /**
     * 上传文件
     *
     * @param file
     * @param qiniuConfig
     * @return
     */
    @CacheEvict(allEntries = true)
    QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig);

    /**
     * 查询文件
     *
     * @param id
     * @return
     */
    @Cacheable(key = "'content:'+#p0")
    QiniuContent findByContentId(Long id);

    /**
     * 下载文件
     *
     * @param content
     * @param config
     * @return
     */
    String download(QiniuContent content, QiniuConfig config);

    /**
     * 删除文件
     *
     * @param content
     * @param config
     * @return
     */
    @CacheEvict(allEntries = true)
    void delete(QiniuContent content, QiniuConfig config);

    /**
     * 同步数据
     *
     * @param config
     */
    @CacheEvict(allEntries = true)
    void synchronize(QiniuConfig config);

    /**
     * 删除文件
     *
     * @param ids
     * @param config
     */
    @CacheEvict(allEntries = true)
    void deleteAll(Long[] ids, QiniuConfig config);
}
