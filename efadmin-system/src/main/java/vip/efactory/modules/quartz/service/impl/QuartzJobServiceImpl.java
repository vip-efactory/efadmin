package vip.efactory.modules.quartz.service.impl;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.quartz.entity.QuartzJob;
import vip.efactory.modules.quartz.repository.QuartzJobRepository;
import vip.efactory.modules.quartz.repository.QuartzLogRepository;
import vip.efactory.modules.quartz.service.QuartzJobService;
import vip.efactory.modules.quartz.service.dto.JobQueryCriteria;
import vip.efactory.modules.quartz.utils.QuartzManage;
import vip.efactory.utils.PageUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;

import java.util.Optional;


@Service(value = "quartzJobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobServiceImpl extends BaseServiceImpl<QuartzJob, Long, QuartzJobRepository> implements QuartzJobService {

    @Autowired
    private QuartzLogRepository quartzLogRepository;

    @Autowired
    private QuartzManage quartzManage;

    @Override
    public Object queryAll(JobQueryCriteria criteria, Pageable pageable) {
        return PageUtil.toPage(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable));
    }

    @Override
    public Object queryAllLog(JobQueryCriteria criteria, Pageable pageable) {
        return PageUtil.toPage(quartzLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable));
    }

    @Override
    public Optional<QuartzJob> findById(Long id) {
        Optional<QuartzJob> quartzJob = br.findById(id);
        ValidationUtil.isNull(quartzJob, "QuartzJob", "id", id);
        return quartzJob;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob create(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())) {
            throw new BadRequestException("cron表达式格式错误");
        }
        resources = br.save(resources);
        quartzManage.addJob(resources);
        return resources;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob update(QuartzJob resources) {
        if (resources.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        if (!CronExpression.isValidExpression(resources.getCronExpression())) {
            throw new BadRequestException("cron表达式格式错误");
        }
        resources = br.save(resources);
        quartzManage.updateJobCron(resources);
        return resources;
    }

    @Override
    public void updateIsPause(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        if (quartzJob.getIsPause()) {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause(false);
        } else {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause(true);
        }
        br.save(quartzJob);
    }

    @Override
    public void execution(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        quartzManage.runAJobNow(quartzJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(QuartzJob quartzJob) {
        if (quartzJob.getId().equals(1L)) {
            throw new BadRequestException("该任务不可操作");
        }
        quartzManage.deleteJob(quartzJob);
        br.delete(quartzJob);
    }
}
