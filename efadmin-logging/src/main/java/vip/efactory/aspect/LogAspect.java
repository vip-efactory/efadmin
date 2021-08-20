package vip.efactory.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import vip.efactory.domain.SysLog;
import vip.efactory.ejpa.tenant.identifier.TenantConstants;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;
import vip.efactory.service.LogService;
import vip.efactory.utils.RequestHolder;
import vip.efactory.utils.SecurityUtils;
import vip.efactory.utils.StringUtils;
import vip.efactory.utils.ThrowableUtil;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Description: 日志注解的实现
 *
 * @author dbdu
 * @date 19-7-10 上午10:42
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private final LogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(vip.efactory.aop.log.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }


    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        SysLog log = new SysLog("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        // 从请求头中获取租户信息
        String tenantId = request.getHeader(TenantConstants.TENANT_ID);
        if (isNotBlank(tenantId)) {
            TenantHolder.setTenantId(Long.valueOf(tenantId));
        }
        log.setRemark("@T" + tenantId); // 记录租户的ｉｄ的同时，也将租户信息传入线程池
        logService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        SysLog log = new SysLog("ERROR", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        // 从请求头中获取租户信息
        String tenantId = request.getHeader(TenantConstants.TENANT_ID);
        if (isNotBlank(tenantId)) {
            TenantHolder.setTenantId(Long.valueOf(tenantId));
        }
        log.setRemark("@T" + tenantId); // 记录租户的ｉｄ的同时，也将租户信息传入线程池
        logService.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), (ProceedingJoinPoint) joinPoint, log);
    }

    public String getUsername() {
        try {
            return SecurityUtils.getUsername();
        } catch (Exception e) {
            return "";
        }
    }
}
