package vip.efactory.modules.mnt.service.impl;


import com.jcraft.jsch.JSchException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.common.base.page.EPage;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.mnt.domain.ServerDeploy;
import vip.efactory.modules.mnt.repository.ServerDeployRepository;
import vip.efactory.modules.mnt.service.ServerDeployService;
import vip.efactory.modules.mnt.service.dto.ServerDeployDto;
import vip.efactory.modules.mnt.service.dto.ServerDeployQueryCriteria;
import vip.efactory.modules.mnt.service.mapper.ServerDeployMapper;
import vip.efactory.modules.mnt.util.ExecuteShellUtil;
import vip.efactory.utils.FileUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = "serverDeploy")
public class ServerDeployServiceImpl extends BaseServiceImpl<ServerDeploy, Long, ServerDeployRepository> implements ServerDeployService {

    private ServerDeployMapper serverDeployMapper;

    public ServerDeployServiceImpl(ServerDeployMapper serverDeployMapper) {
        this.serverDeployMapper = serverDeployMapper;
    }

    @Override
    @Cacheable
    public Object queryAll(ServerDeployQueryCriteria criteria, Pageable pageable) {
        Page<ServerDeploy> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return new EPage(page.map(serverDeployMapper::toDto));
    }

    @Override
    @Cacheable
    public List<ServerDeployDto> queryAll(ServerDeployQueryCriteria criteria) {
        return serverDeployMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Cacheable
    public ServerDeployDto findDtoById(Long id) {
        ServerDeploy server = br.findById(id).orElseGet(ServerDeploy::new);
        ValidationUtil.isNull(server.getId(), "ServerDeploy", "id", id);
        return serverDeployMapper.toDto(server);
    }

    @Override
    @Cacheable
    public ServerDeployDto findByIp(String ip) {
        ServerDeploy deploy = br.findByIp(ip);
        return serverDeployMapper.toDto(deploy);
    }

    @Override
    public Boolean testConnect(ServerDeploy resources) throws JSchException {
        ExecuteShellUtil executeShellUtil = null;
        try {
            executeShellUtil = new ExecuteShellUtil(resources.getIp(), resources.getAccount(), resources.getPassword(), resources.getPort());
            return executeShellUtil.execute("ls") == 0;
        } finally {
            if (executeShellUtil != null) {
                executeShellUtil.close();
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public ServerDeployDto create(ServerDeploy resources) {
        return serverDeployMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void update2(ServerDeploy resources) {
        ServerDeploy serverDeploy = br.findById(resources.getId()).orElseGet(ServerDeploy::new);
        ValidationUtil.isNull(serverDeploy.getId(), "ServerDeploy", "id", resources.getId());
        serverDeploy.copy(resources);
        br.save(serverDeploy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            br.deleteById(id);
        }
    }

    @Override
    public void download(List<ServerDeployDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ServerDeployDto deployDto : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("服务器名称", deployDto.getName());
            map.put("服务器IP", deployDto.getIp());
            map.put("端口", deployDto.getPort());
            map.put("账号", deployDto.getAccount());
            map.put("创建日期", deployDto.getCreateTime().toString());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
