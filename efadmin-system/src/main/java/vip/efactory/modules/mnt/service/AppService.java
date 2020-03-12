package vip.efactory.modules.mnt.service;


import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.mnt.domain.App;
import vip.efactory.modules.mnt.service.dto.AppDto;
import vip.efactory.modules.mnt.service.dto.AppQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
public interface AppService extends IBaseService<App, Long> {

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(AppQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     *
     * @param criteria 条件
     * @return /
     */
    List<AppDto> queryAll(AppQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    AppDto findDtoById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    AppDto create(App resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update2(App resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 导出数据
     *
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void download(List<AppDto> queryAll, HttpServletResponse response) throws IOException;
}
