package vip.efactory.modules.mnt.service.impl;

import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.common.base.page.EPage;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.mnt.domain.DeployHistory;
import vip.efactory.modules.mnt.repository.DeployHistoryRepository;
import vip.efactory.modules.mnt.service.DeployHistoryService;
import vip.efactory.modules.mnt.service.dto.DeployHistoryDto;
import vip.efactory.modules.mnt.service.dto.DeployHistoryQueryCriteria;
import vip.efactory.modules.mnt.service.mapper.DeployHistoryMapper;
import vip.efactory.utils.FileUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeployHistoryServiceImpl extends BaseServiceImpl<DeployHistory, String, DeployHistoryRepository> implements DeployHistoryService {

    private final DeployHistoryMapper deployhistoryMapper;

    public DeployHistoryServiceImpl(DeployHistoryMapper deployhistoryMapper) {
        this.deployhistoryMapper = deployhistoryMapper;
    }

    @Override
    public Object queryAll(DeployHistoryQueryCriteria criteria, Pageable pageable) {
        Page<DeployHistory> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return new EPage(page.map(deployhistoryMapper::toDto));
    }

    @Override
    public List<DeployHistoryDto> queryAll(DeployHistoryQueryCriteria criteria) {
        return deployhistoryMapper.toDto(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    public DeployHistoryDto findDtoById(String id) {
        DeployHistory deployhistory = br.findById(id).orElseGet(DeployHistory::new);
        ValidationUtil.isNull(deployhistory.getId(), "DeployHistory", "id", id);
        return deployhistoryMapper.toDto(deployhistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeployHistoryDto create(DeployHistory resources) {
        resources.setId(IdUtil.simpleUUID());
        return deployhistoryMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        for (String id : ids) {
            br.deleteById(id);
        }
    }

    @Override
    public void download(List<DeployHistoryDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DeployHistoryDto deployHistoryDto : queryAll) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("部署编号", deployHistoryDto.getDeployId());
            map.put("应用名称", deployHistoryDto.getAppName());
            map.put("部署IP", deployHistoryDto.getIp());
            map.put("部署时间", deployHistoryDto.getDeployDate());
            map.put("部署人员", deployHistoryDto.getDeployUser());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
