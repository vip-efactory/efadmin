package vip.efactory.modules.quartz.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.quartz.CronExpression;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vip.efactory.common.base.page.EPage;
import vip.efactory.ejpa.base.service.impl.BaseServiceImpl;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.quartz.domain.QuartzJob;
import vip.efactory.modules.quartz.domain.QuartzLog;
import vip.efactory.modules.quartz.repository.QuartzJobRepository;
import vip.efactory.modules.quartz.repository.QuartzLogRepository;
import vip.efactory.modules.quartz.service.QuartzJobService;
import vip.efactory.modules.quartz.service.dto.JobQueryCriteria;
import vip.efactory.modules.quartz.utils.QuartzManage;
import vip.efactory.utils.FileUtil;
import vip.efactory.utils.QueryHelp;
import vip.efactory.utils.ValidationUtil;


@Service(value = "quartzJobService")
@CacheConfig(cacheNames = "quartzJob")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobServiceImpl extends BaseServiceImpl<QuartzJob, Long, QuartzJobRepository> implements QuartzJobService {

    private final QuartzLogRepository quartzLogRepository;

    private final QuartzManage quartzManage;

    public QuartzJobServiceImpl( QuartzLogRepository quartzLogRepository, QuartzManage quartzManage) {
        this.quartzLogRepository = quartzLogRepository;
        this.quartzManage = quartzManage;
    }

    @Override
    public Object queryAll(JobQueryCriteria criteria, Pageable pageable) {
        return new EPage(br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable));
    }

    @Override
    public Object queryAllLog(JobQueryCriteria criteria, Pageable pageable) {
        return new EPage(quartzLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable));
    }

    @Override
    public List<QuartzJob> queryAll(JobQueryCriteria criteria) {
        return br.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    public List<QuartzLog> queryAllLog(JobQueryCriteria criteria) {
        return quartzLogRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
    }

    @Override
    @Cacheable(key = "#p0")
    public QuartzJob findById2(Long id) {
        QuartzJob quartzJob = br.findById(id).orElseGet(QuartzJob::new);
        ValidationUtil.isNull(quartzJob.getId(),"QuartzJob","id",id);
        return quartzJob;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public QuartzJob create(QuartzJob resources) {
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            throw new BadRequestException("cron表达式格式错误");
        }
        resources = br.save(resources);
        quartzManage.addJob(resources);
        return resources;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update2(QuartzJob resources) {
        if(resources.getId().equals(1L)){
            throw new BadRequestException("该任务不可操作");
        }
        if (!CronExpression.isValidExpression(resources.getCronExpression())){
            throw new BadRequestException("cron表达式格式错误");
        }
        resources = br.save(resources);
        quartzManage.updateJobCron(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void updateIsPause(QuartzJob quartzJob) {
        if(quartzJob.getId().equals(1L)){
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
        if(quartzJob.getId().equals(1L)){
            throw new BadRequestException("该任务不可操作");
        }
        quartzManage.runJobNow(quartzJob);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            if(id.equals(1L)){
                throw new BadRequestException("更新访客记录不可删除，你可以在后台代码中取消该限制");
            }
            QuartzJob quartzJob = findById2(id);
            quartzManage.deleteJob(quartzJob);
            br.delete(quartzJob);
        }
    }

    @Override
    public void download(List<QuartzJob> quartzJobs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzJob quartzJob : quartzJobs) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("任务名称", quartzJob.getJobName());
            map.put("Bean名称", quartzJob.getBeanName());
            map.put("执行方法", quartzJob.getMethodName());
            map.put("参数", quartzJob.getParams());
            map.put("表达式", quartzJob.getCronExpression());
            map.put("状态", quartzJob.getIsPause() ? "暂停中" : "运行中");
            map.put("描述", quartzJob.getRemark());
            map.put("创建日期", quartzJob.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void downloadLog(List<QuartzLog> queryAllLog, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (QuartzLog quartzLog : queryAllLog) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("任务名称", quartzLog.getJobName());
            map.put("Bean名称", quartzLog.getBeanName());
            map.put("执行方法", quartzLog.getMethodName());
            map.put("参数", quartzLog.getParams());
            map.put("表达式", quartzLog.getCronExpression());
            map.put("异常详情", quartzLog.getExceptionDetail());
            map.put("耗时/毫秒", quartzLog.getTime());
            map.put("状态", quartzLog.getIsSuccess() ? "成功" : "失败");
            map.put("创建日期", quartzLog.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
