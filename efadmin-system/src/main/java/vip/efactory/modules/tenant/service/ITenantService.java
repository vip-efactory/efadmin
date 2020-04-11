package vip.efactory.modules.tenant.service;

import vip.efactory.modules.tenant.domain.Tenant;
import vip.efactory.modules.tenant.service.dto.TenantDto;
import vip.efactory.modules.tenant.service.dto.TenantQueryCriteria;
import vip.efactory.ejpa.base.service.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* 系统租户 服务层
* @author vip-efactory
* @date 2020-04-11
*/
public interface ITenantService extends IBaseService<Tenant, Long>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Page<TenantDto>
    */
    Page<TenantDto> queryAll(TenantQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<TenantDto>
    */
 //   List<TenantDto> queryAll(TenantQueryCriteria criteria);

    /**
     * 根据ID查询TenantDto
     * @param id ID
     * @return TenantDto
     */
 //   TenantDto findDtoById(Long id);

    /**
    * 创建
    * @param resources /
    * @return TenantDto
    */
 //   TenantDto create(Tenant resources);

    /**
    * 编辑,为避免和jpa默认实现冲突，方法名update==>edit
    * @param resources /
    */
 //   void edit(Tenant resources);


    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<TenantDto> all, HttpServletResponse response) throws IOException;
}