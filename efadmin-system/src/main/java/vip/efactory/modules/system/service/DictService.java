package vip.efactory.modules.system.service;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.domain.Dict;
import vip.efactory.modules.system.service.dto.DictDto;
import vip.efactory.modules.system.service.dto.DictQueryCriteria;

public interface DictService extends IBaseService<Dict, Long> {

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(DictQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param dict /
     * @return /
     */
    List<DictDto> queryAll(DictQueryCriteria dict);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    DictDto findDtoById(Long id);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    DictDto create(Dict resources);

    /**
     * 编辑
     * @param resources /
     */
    void update2(Dict resources);

    /**
     * 删除
     * @param id /
     */
    void delete(Long id);

    /**
     * 导出数据
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DictDto> queryAll, HttpServletResponse response) throws IOException;
}