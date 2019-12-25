package vip.efactory.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.modules.system.entity.Job;
import vip.efactory.modules.system.repository.DeptRepository;
import vip.efactory.modules.system.repository.JobRepository;
import vip.efactory.modules.system.service.JobService;
import vip.efactory.modules.system.service.dto.JobDTO;
import vip.efactory.modules.system.service.dto.JobQueryCriteria;
import vip.efactory.modules.system.service.mapper.JobMapper;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends BaseServiceImpl<Job, Long, JobRepository> implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private DeptRepository deptRepository;

    @Override
    public Object queryAll(JobQueryCriteria criteria, Pageable pageable) {
        Page<Job> page = br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        List<JobDTO> jobs = new ArrayList<>();
        for (Job job : page.getContent()) {
            jobs.add(jobMapper.toDto(job, deptRepository.findNameById(job.getDept().getPid())));
        }
        return PageUtil.toPage(jobs, page.getTotalElements());
    }

    @Override
    public JobDTO findJobDTOById(Long id) {
        Optional<Job> job = br.findById(id);
        ValidationUtil.isNull(job, "Job", "id", id);
        return jobMapper.toDto(job.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobDTO create(Job resources) {
        return jobMapper.toDto(br.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Job update(Job resources) {
        Optional<Job> optionalJob = br.findById(resources.getId());
        ValidationUtil.isNull(optionalJob, "Job", "id", resources.getId());

        Job job = optionalJob.get();
        resources.setId(job.getId());
        br.save(resources);
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        br.deleteById(id);
    }
}
