package vip.efactory.modules.quartz.utils;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import vip.efactory.config.thread.ThreadPoolExecutorUtil;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;
import vip.efactory.ejpa.utils.SpringContextHolder;
import vip.efactory.modules.quartz.domain.QuartzJob;
import vip.efactory.modules.quartz.domain.QuartzLog;
import vip.efactory.modules.quartz.repository.QuartzLogRepository;
import vip.efactory.modules.quartz.service.QuartzJobService;
import vip.efactory.utils.ThrowableUtil;

/**
 * 参考人人开源，https://gitee.com/renrenio/renren-security
 */
@Async
public class ExecutionJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 线程同步锁
    private final Lock lock = new ReentrantLock();

    /** 该处仅供参考 */
    private final static ThreadPoolExecutor EXECUTOR = ThreadPoolExecutorUtil.getPoll();

    @Override
    @SuppressWarnings("unchecked")
    protected void executeInternal(JobExecutionContext context) {

        // 获取所有的要执行的定时任务，逐个处理,
        context.getMergedJobDataMap().entrySet().forEach(entry -> {
            // QuartzJob quartzJob = (QuartzJob)
            // context.getMergedJobDataMap().get(QuartzJob.JOB_KEY);
            QuartzJob quartzJob = (QuartzJob) entry.getValue();
            Long defaultTenantId = TenantHolder.getTenantId();
            // 需要获取当前定时任务的租户信息
            Long tenantId = Long.valueOf(entry.getKey().split("@")[1]); // 0是前缀，1是租户id，2是任务在数据库里表字段的id

            lock.lock();
            TenantHolder.setTenantId(tenantId);   // 更改为定时任务所属的租户
            // 获取spring bean
            QuartzLogRepository quartzLogRepository = SpringContextHolder.getBean(QuartzLogRepository.class);
            QuartzJobService quartzJobService = SpringContextHolder.getBean(QuartzJobService.class);

            QuartzLog log = new QuartzLog();
            log.setJobName(quartzJob.getJobName());
            log.setBeanName(quartzJob.getBeanName());
            log.setMethodName(quartzJob.getMethodName());
            log.setParams(quartzJob.getParams());
            long startTime = System.currentTimeMillis();
            log.setCronExpression(quartzJob.getCronExpression());

            try {
                // 执行任务
                logger.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
                QuartzRunnable task = new QuartzRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(),
                        quartzJob.getParams());
                Future<?> future = EXECUTOR.submit(task);
                future.get();
                long times = System.currentTimeMillis() - startTime;
                log.setTime(times);
                // 任务状态
                log.setIsSuccess(true);
                logger.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), times);
            } catch (Exception e) {
                logger.error("任务执行失败，任务名称：{}" + quartzJob.getJobName(), e);
                long times = System.currentTimeMillis() - startTime;
                log.setTime(times);
                // 任务状态 0：成功 1：失败
                log.setIsSuccess(false);
                log.setExceptionDetail(ThrowableUtil.getStackTrace(e));
                quartzJob.setIsPause(false);
                // 更新状态
                quartzJobService.updateIsPause(quartzJob);
            } finally {
                log.setRemark("Tenant " + tenantId + " 的定时任务"); // 故意写备注，这样如果错乱从日志里还是能发现的
                quartzLogRepository.save(log);
            }
            TenantHolder.setTenantId(defaultTenantId);   // 还原为默认的租户信息
            lock.unlock();
        });
    }
}
