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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface QiNiuService extends IBaseService<QiniuConfig, Long> {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(QiniuQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     * @param criteria 条件
     * @return /
     */
    List<QiniuContent> queryAll(QiniuQueryCriteria criteria);

    /**
     * 查配置
     * @return QiniuConfig
     */
    QiniuConfig find();

    /**
     * 修改配置
     * @param qiniuConfig 配置
     * @return QiniuConfig
     */
    QiniuConfig update(QiniuConfig qiniuConfig);

    /**
     * 上传文件
     * @param file 文件
     * @param qiniuConfig 配置
     * @return QiniuContent
     */
    QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig);

    /**
     * 查询文件
     * @param id 文件ID
     * @return QiniuContent
     */
    QiniuContent findByContentId(Long id);

    /**
     * 下载文件
     * @param content 文件信息
     * @param config 配置
     * @return String
     */
    String download(QiniuContent content, QiniuConfig config);

    /**
     * 删除文件
     * @param content 文件
     * @param config 配置
     */
    void delete(QiniuContent content, QiniuConfig config);

    /**
     * 同步数据
     * @param config 配置
     */
    void synchronize(QiniuConfig config);

    /**
     * 删除文件
     * @param ids 文件ID数组
     * @param config 配置
     */
    void deleteAll(Long[] ids, QiniuConfig config);

    /**
     * 更新数据
     * @param type 类型
     */
    void update(String type);

    /**
     * 导出数据
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void downloadList(List<QiniuContent> queryAll, HttpServletResponse response) throws IOException;
}
