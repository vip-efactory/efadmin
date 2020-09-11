package vip.efactory.modules.mnt.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.common.base.page.EPage;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.mnt.domain.App;
import vip.efactory.modules.mnt.repository.AppRepository;
import vip.efactory.modules.mnt.service.AppService;
import vip.efactory.modules.mnt.service.dto.AppDto;
import vip.efactory.modules.mnt.service.dto.AppQueryCriteria;
import vip.efactory.modules.mnt.service.mapper.AppMapper;
import vip.efactory.utils.FileUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author zhanghouying
 * @date 2019-08-24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AppServiceImpl extends BaseServiceImpl<App, Long, AppRepository> implements AppService {
    private AppMapper appMapper;

    public AppServiceImpl( AppMapper appMapper) {
        this.appMapper = appMapper;
    }

    @Override
    public Object queryAll(AppQueryCriteria criteria, Pageable pageable) {
        Page<App> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return new EPage(page.map(appMapper::toDto));
    }

    @Override
    public List<AppDto> queryAll(AppQueryCriteria criteria) {
        return appMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public AppDto findDtoById(Long id) {
        App app = br.findById(id).orElseGet(App::new);
        ValidationUtil.isNull(app.getId(), "App", "id", id);
        return appMapper.toDto(app);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppDto create(App resources) {
        verification(resources);
        return appMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update2(App resources) {
        verification(resources);
        App app = br.findById(resources.getId()).orElseGet(App::new);
        ValidationUtil.isNull(app.getId(), "App", "id", resources.getId());
        app.copy(resources);
        br.save(app);
    }

    private void verification(App resources) {
        String opt = "/opt";
        String home = "/home";
        if (!(resources.getUploadPath().startsWith(opt) || resources.getUploadPath().startsWith(home))) {
            throw new BadRequestException("文件只能上传在opt目录或者home目录 ");
        }
        if (!(resources.getDeployPath().startsWith(opt) || resources.getDeployPath().startsWith(home))) {
            throw new BadRequestException("文件只能部署在opt目录或者home目录 ");
        }
        if (!(resources.getBackupPath().startsWith(opt) || resources.getBackupPath().startsWith(home))) {
            throw new BadRequestException("文件只能备份在opt目录或者home目录 ");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            br.deleteById(id);
        }
    }

    @Override
    public void download(List<AppDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AppDto appDto : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("应用名称", appDto.getName());
            map.put("端口", appDto.getPort());
            map.put("上传目录", appDto.getUploadPath());
            map.put("部署目录", appDto.getDeployPath());
            map.put("备份目录", appDto.getBackupPath());
            map.put("启动脚本", appDto.getStartScript());
            map.put("部署脚本", appDto.getDeployScript());
            map.put("创建日期", appDto.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
