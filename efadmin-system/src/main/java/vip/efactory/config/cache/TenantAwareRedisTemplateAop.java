package vip.efactory.config.cache;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;

/**
 * @author James Wu
 * <p>
 * determine redis template dbindex on method and tenant
 */
@ConditionalOnProperty("spring.redis.host")
@Component
@Aspect
@Order(1)
@Slf4j
public class TenantAwareRedisTemplateAop {

    @Pointcut(value = "!@within(vip.efactory.config.cache.UseSharedCacheDbConfig)"
            + " && ( @annotation(org.springframework.cache.annotation.Cacheable) "
            + " ||  @annotation(org.springframework.cache.annotation.CacheEvict) "
            + " ||  @annotation(org.springframework.cache.annotation.CachePut) )")
    public void multiTenantRedisConfig() { }

    @Pointcut(value = "@within(vip.efactory.config.cache.UseSharedCacheDbConfig)"
            + " && ( @annotation(org.springframework.cache.annotation.Cacheable) "
            + " ||  @annotation(org.springframework.cache.annotation.CacheEvict) "
            + " ||  @annotation(org.springframework.cache.annotation.CachePut) )")
    public void defaultRedisConfig() {

    }

    @Before(value = "defaultRedisConfig()")
    public void beforeDefaultRedisConfig(JoinPoint joinPoint) {
        DynamicRedisTemplate.setCurrentDbIndex(0);
        log.debug("【redis路由器】database 0;method:" + joinPoint.toString());
    }

    @After(value = "defaultRedisConfig()")
    public void afterDefaultRedisCacheMethod(JoinPoint joinPoint) {
        DynamicRedisTemplate.remove();
    }

    @Before(value = "multiTenantRedisConfig()")
    public void beforeTenantAwareRedisCacheMethod(JoinPoint joinPoint) {
        if (TenantHolder.getTenantId() == null) {
            log.error("【redis路由器】error:缓存路由无法选取对应redis database，因为线程上没有租户信息, method:" + joinPoint.toString() + " ");
            throw new RuntimeException("error:缓存路由无法选取对应redis database，因为线程上没有租户信息");
        } else {
            DynamicRedisTemplate.setCurrentDbIndex(TenantHolder.getTenantId().intValue());
            log.debug("【redis路由器】database " + TenantHolder.getTenantId().intValue() + ";method:" + joinPoint.toString());
        }
    }

    @After(value = "multiTenantRedisConfig()")
    public void afterTenantAwareRedisCacheMethod(JoinPoint joinPoint) {
        DynamicRedisTemplate.remove();
    }
}
