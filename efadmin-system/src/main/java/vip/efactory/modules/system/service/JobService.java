package vip.efactory.modules.system.service;


import org.springframework.data.domain.Pageable;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.entity.Job;
import vip.efactory.modules.system.service.dto.JobDto;
import vip.efactory.modules.system.service.dto.JobQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface JobService extends IBaseService<Job, Long> {

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    JobDto findDtoById(Long id);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    JobDto create(Job resources);

    /**
     * 编辑
     * @param resources /
     */
    void update2(Job resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String,Object> queryAll(JobQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     * @param criteria /
     * @return /
     */
    List<JobDto> queryAll(JobQueryCriteria criteria);

    /**
     * 导出数据
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<JobDto> queryAll, HttpServletResponse response) throws IOException;
}