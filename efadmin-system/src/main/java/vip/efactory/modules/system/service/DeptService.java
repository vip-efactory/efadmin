package vip.efactory.modules.system.service;

import org.springframework.cache.annotation.CacheConfig;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.domain.Dept;
import vip.efactory.modules.system.service.dto.DeptDto;
import vip.efactory.modules.system.service.dto.DeptQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@CacheConfig(cacheNames = "dept")
public interface DeptService extends IBaseService<Dept, Long> {

    /**
     * 查询所有数据
     * @param criteria 条件
     * @return /
     */
    List<DeptDto> queryAll(DeptQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    DeptDto findDtoById(Long id);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    DeptDto create(Dept resources);

    /**
     * 编辑
     * @param resources /
     */
    void update2(Dept resources);

    /**
     * 删除
     * @param deptDtos /
     *
     */
    void delete(Set<DeptDto> deptDtos);

    /**
     * 构建树形数据
     * @param deptDtos 原始数据
     * @return /
     */
    Object buildTree(List<DeptDto> deptDtos);

    Object buildTree4Entites(List<Dept> depts);

    /**
     * 根据PID查询
     * @param pid /
     * @return /
     */
    List<Dept> findByPid(long pid);

    /**
     * 根据角色ID查询
     * @param id /
     * @return /
     */
    Set<Dept> findByRoleIds(Long id);

    /**
     * 导出数据
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DeptDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取待删除的部门
     * @param deptList /
     * @param deptDtos /
     * @return /
     */
    Set<DeptDto> getDeleteDepts(List<Dept> deptList, Set<DeptDto> deptDtos);
}
