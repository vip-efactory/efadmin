package vip.efactory.modules.quartz.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vip.efactory.ejpa.tenant.identifier.TenantConstants;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;
import vip.efactory.modules.quartz.domain.QuartzJob;
import vip.efactory.modules.quartz.repository.QuartzJobRepository;
import vip.efactory.modules.quartz.utils.QuartzManage;
import vip.efactory.modules.tenant.domain.Tenant;
import vip.efactory.modules.tenant.service.ITenantService;

@Component
@AllArgsConstructor
@Slf4j
public class JobRunner implements ApplicationRunner {

    private final QuartzJobRepository quartzJobRepository;
    private final QuartzManage quartzManage;
    private ITenantService tenantService;

    /**
     * 项目启动时重新激活启用的定时任务
     * 
     * @param applicationArguments /
     */
    @Override
    public void run(ApplicationArguments applicationArguments) {
        log.info("--------------------注入定时任务---------------------");
        // 对每个租户的定时任务都进行注入
        // 从默认数据源里查询所有的启用的租户信息，
        if (TenantHolder.getTenantId() == TenantConstants.DEFAULT_TENANT_ID) {
            log.info("开始注入默认租户的定时任务...");
            List<QuartzJob> quartzJobs = quartzJobRepository.findByIsPauseIsFalse();
            quartzJobs.forEach(quartzManage::addJob);
            List<Tenant> tenants = tenantService.findAllByStatusEquals(ITenantService.TENANT_ENABLE);
            tenants.forEach(tenant -> {
                log.info("开始注入租户{}的定时任务...", tenant.getId());
                // 先设定租户的持有者信息
                TenantHolder.setTenantId(tenant.getId());
                List<QuartzJob> jobs = quartzJobRepository.findByIsPauseIsFalse();
                jobs.forEach(quartzManage::addJob);                
            });
            // 还原租户的持有者信息
            TenantHolder.setTenantId(TenantConstants.DEFAULT_TENANT_ID);
        }

        log.info("--------------------定时任务注入完成---------------------");
    }
}
