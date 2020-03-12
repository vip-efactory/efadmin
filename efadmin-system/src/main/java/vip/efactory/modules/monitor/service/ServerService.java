package vip.efactory.modules.monitor.service;


import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.monitor.domain.Server;
import vip.efactory.modules.monitor.service.dto.ServerDTO;
import vip.efactory.modules.monitor.service.dto.ServerQueryCriteria;

import java.util.List;
import java.util.Set;

/**
 * @author Zhang houying
 * @date 2019-11-03
 */
public interface ServerService extends IBaseService<Server, Integer> {

    /**
     * 查询数据分页
     *
     * @param criteria 条件参数
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Object queryAll(ServerQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<ServerDTO>
     */
    List<ServerDTO> queryAll(ServerQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return ServerDTO
     */
    ServerDTO findDtoById(Integer id);

    /**
     * 创建服务监控
     *
     * @param resources /
     * @return /
     */
    ServerDTO create(Server resources);

    /**
     * 编辑服务监控
     *
     * @param resources /
     */
    void update2(Server resources);

    /**
     * 删除
     *
     * @param id /
     */
    void delete(Set<Integer> id);

}
