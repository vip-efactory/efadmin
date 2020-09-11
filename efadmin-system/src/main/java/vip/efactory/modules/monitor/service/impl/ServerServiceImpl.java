package vip.efactory.modules.monitor.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.common.base.page.EPage;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.monitor.domain.Server;
import vip.efactory.modules.monitor.repository.ServerRepository;
import vip.efactory.modules.monitor.service.ServerService;
import vip.efactory.modules.monitor.service.dto.ServerDTO;
import vip.efactory.modules.monitor.service.dto.ServerQueryCriteria;
import vip.efactory.modules.monitor.service.mapper.ServerMapper;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.List;
import java.util.Set;

/**
 * @author Zhang houying
 * @date 2019-11-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ServerServiceImpl extends BaseServiceImpl<Server, Integer, ServerRepository> implements ServerService {
    private final ServerMapper serverMapper;

    public ServerServiceImpl(ServerMapper serverMapper) {
        this.serverMapper = serverMapper;
    }

    @Override
    public Object queryAll(ServerQueryCriteria criteria, Pageable pageable) {
        Page<Server> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        page.forEach(server -> {
            try {
                server.setState("1");
                String url = String.format("http://%s:%d/api/serverMonitor", server.getAddress(), server.getPort());
                String res = HttpUtil.get(url, 1000);
                JSONObject obj = JSONObject.parseObject(res);
                server.setCpuRate(obj.getDouble("cpuRate"));
                server.setCpuCore(obj.getInteger("cpuCore"));
                server.setMemTotal(obj.getDouble("memTotal"));
                server.setMemUsed(obj.getDouble("memUsed"));
                server.setDiskTotal(obj.getDouble("diskTotal"));
                server.setDiskUsed(obj.getDouble("diskUsed"));
                server.setSwapTotal(obj.getDouble("swapTotal"));
                server.setSwapUsed(obj.getDouble("swapUsed"));
            } catch (Exception e) {
                server.setState("0");
                e.printStackTrace();
            }
        });

        return new EPage(page.map(serverMapper::toDto));
    }

    @Override
    public List<ServerDTO> queryAll(ServerQueryCriteria criteria) {
        return serverMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public ServerDTO findDtoById(Integer id) {
        Server server = br.findById(id).orElseGet(Server::new);
        ValidationUtil.isNull(server.getId(), "Server", "id", id);
        return serverMapper.toDto(server);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerDTO create(Server resources) {
        return serverMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update2(Server resources) {
        Server server = br.findById(resources.getId()).orElseGet(Server::new);
        ValidationUtil.isNull(server.getId(), "Server", "id", resources.getId());
        server.copy(resources);
        br.save(server);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        for (Integer id : ids) {
            br.deleteById(id);
        }
    }

}
