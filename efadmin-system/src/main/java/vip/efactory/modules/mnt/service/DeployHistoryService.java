package vip.efactory.modules.mnt.service;


import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.mnt.domain.DeployHistory;
import vip.efactory.modules.mnt.service.dto.DeployHistoryDto;
import vip.efactory.modules.mnt.service.dto.DeployHistoryQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author zhanghouying
 */
public interface DeployHistoryService extends IBaseService<DeployHistory, String> {

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(DeployHistoryQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     *
     * @param criteria 条件
     * @return /
     */
    List<DeployHistoryDto> queryAll(DeployHistoryQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    DeployHistoryDto findDtoById(String id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    DeployHistoryDto create(DeployHistory resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<String> ids);

    /**
     * 导出数据
     *
     * @param queryAll /
     * @param response /
     * @throws IOException /
     */
    void download(List<DeployHistoryDto> queryAll, HttpServletResponse response) throws IOException;
}
