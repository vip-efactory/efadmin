package vip.efactory.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import vip.efactory.ejpa.tenant.identifier.TenantConstants;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 租户信息拦截器，获取到租户信息后设定该租户使用的redis缓存数据库索引号
 * 如果获取不到就使用默认的租户的索引号。
 */
@Slf4j
public class RedisTenantInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getHeader(TenantConstants.TENANT_ID);
        if (!StringUtils.isEmpty(tenantId)) {
            DynamicRedisTemplate.setCurrentDbIndex(Integer.parseInt(tenantId));
        } else {
            DynamicRedisTemplate.setCurrentDbIndex(TenantConstants.DEFAULT_TENANT_ID.intValue());
            // 此处不进行抛出异常，为了兼容非多租户模式，没有就默认为租户0L
            log.warn("请求中没有租户信息，使用默认的租户ID为:{}对应的Redis", TenantConstants.DEFAULT_TENANT_ID);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
         DynamicRedisTemplate.remove();
    }

}
