package vip.efactory.modules.system.service;



import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import vip.efactory.ejpa.base.service.IBaseService;
import vip.efactory.modules.system.entity.Role;
import vip.efactory.modules.system.service.dto.RoleDto;
import vip.efactory.modules.system.service.dto.RoleQueryCriteria;
import vip.efactory.modules.system.service.dto.RoleSmallDto;
import vip.efactory.modules.system.service.dto.UserDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleService extends IBaseService<Role, Long> {

    /**
     * 根据ID查询
     * @param id /
     * @return /
     */
    RoleDto findDtoById(long id);

    /**
     * 创建
     * @param resources /
     * @return /
     */
    RoleDto create(Role resources);

    /**
     * 编辑
     * @param resources /
     */
    void update2(Role resources);

    /**
     * 删除
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据用户ID查询
     * @param id 用户ID
     * @return /
     */
    List<RoleSmallDto> findByUsersId(Long id);

    /**
     * 根据角色查询角色级别
     * @param roles /
     * @return /
     */
    Integer findByRoles(Set<Role> roles);

    /**
     * 修改绑定的菜单
     * @param resources /
     * @param roleDTO /
     */
    void updateMenu(Role resources, RoleDto roleDTO);

    /**
     * 解绑菜单
     * @param id /
     */
    void untiedMenu(Long id);

    /**
     * 不带条件分页查询
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(Pageable pageable);

    /**
     * 待条件分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(RoleQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     * @param criteria 条件
     * @return /
     */
    List<RoleDto> queryAll(RoleQueryCriteria criteria);

    /**
     * 导出数据
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<RoleDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    Collection<GrantedAuthority> mapToGrantedAuthorities(UserDto user);
}
