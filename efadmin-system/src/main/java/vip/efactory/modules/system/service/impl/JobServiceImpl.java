package vip.efactory.modules.system.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.controller.EPage;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.system.domain.Job;
import vip.efactory.modules.system.repository.DeptRepository;
import vip.efactory.modules.system.repository.JobRepository;
import vip.efactory.modules.system.service.JobService;
import vip.efactory.modules.system.service.dto.JobDto;
import vip.efactory.modules.system.service.dto.JobQueryCriteria;
import vip.efactory.modules.system.service.mapper.JobMapper;
import vip.efactory.utils.FileUtil;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@CacheConfig(cacheNames = "job")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends BaseServiceImpl<Job, Long, JobRepository> implements JobService {

    private final JobMapper jobMapper;

    private final DeptRepository deptRepository;

    public JobServiceImpl(JobMapper jobMapper, DeptRepository deptRepository) {
        this.jobMapper = jobMapper;
        this.deptRepository = deptRepository;
    }

    @Override
//    @Cacheable, 如果缓存会导致排序失效！
    public Object queryAll(JobQueryCriteria criteria, Pageable pageable) {
        Page<Job> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<JobDto> jobs = new ArrayList<>();
        for (Job job : page.getContent()) {
            jobs.add(jobMapper.toDto(job,deptRepository.findNameById(job.getDept().getPid())));
        }
        EPage epage = new EPage();
        epage.setContent(jobs);
        epage.setTotalCount(page.getTotalElements());
        return epage;
    }

    @Override
    @Cacheable
    public List<JobDto> queryAll(JobQueryCriteria criteria) {
        List<Job> list = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
        return jobMapper.toDto(list);
    }

    @Override
    @Cacheable(key = "#p0")
    public JobDto findDtoById(Long id) {
        Job job = br.findById(id).orElseGet(Job::new);
        ValidationUtil.isNull(job.getId(),"Job","id",id);
        return jobMapper.toDto(job);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public JobDto create(Job resources) {
        return jobMapper.toDto(br.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update2(Job resources) {
        Job job = br.findById(resources.getId()).orElseGet(Job::new);
        ValidationUtil.isNull( job.getId(),"Job","id",resources.getId());
        resources.setId(job.getId());
        br.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            br.deleteById(id);
        }
    }

    @Override
    public void download(List<JobDto> jobDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (JobDto jobDTO : jobDtos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("岗位名称", jobDTO.getName());
            map.put("所属部门", jobDTO.getDept().getName());
            map.put("岗位状态", jobDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", jobDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}