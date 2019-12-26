package vip.efactory.modules.system.service;

import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.entity.DictDetail;
import vip.efactory.modules.system.service.dto.DictDetailDto;
import vip.efactory.modules.system.service.dto.DictDetailQueryCriteria;

import java.util.Map;

public interface DictDetailService extends IBaseService<DictDetail, Long> {

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    DictDetailDto findDtoById(Long id);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    DictDetailDto create(DictDetail resources);

    /**
     * 编辑
     * @param resources /
     */
    void update2(DictDetail resources);

    /**
     * 删除
     * @param id /
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable);
}