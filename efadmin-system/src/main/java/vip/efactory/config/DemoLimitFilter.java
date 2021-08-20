package vip.efactory.config;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import vip.efactory.common.base.utils.R;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * 线上演示环境限制操作的过滤器,为防止演示环境被严重破坏，实际项目中可以不需要此过滤器！！
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Component
public class DemoLimitFilter extends GenericFilterBean {
    private static final Set<String> LIMIT_COMPONENTS = new HashSet<>();
    private static final Set<String> LIMIT_METHODS = new HashSet<>();

    @Value("${demoEnv.enableLimit}")
    private Boolean enableLimit;   // 默认不启用

    static {
        // 初始化要限制的组件
        LIMIT_COMPONENTS.add("tenant");
        LIMIT_COMPONENTS.add("users");
        LIMIT_COMPONENTS.add("roles");
        LIMIT_COMPONENTS.add("menus");

        // 初始化要限制的方法
        LIMIT_METHODS.add("DELETE");
        LIMIT_METHODS.add("PUT");
    }

    /**
     * 从请求投头种获取请求的域名(Host) url method,符合条件的将不允许进行操作
     */
    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 如果是演示环境的主机则进行拦截，否则放行
        if (this.enableLimit) {
            // 检查url,method等信息是否合法
            String url = request.getRequestURI();
            String method = request.getMethod();
            String component = url.split("/")[2]; // 例如：/auth/code/   0是空,1是auth,2是code
            // 限制组件和方法都符合，
            if (LIMIT_METHODS.contains(method.toUpperCase()) && LIMIT_COMPONENTS.contains(component.toLowerCase())) {
                R<String> r = R.error(new Exception("演示环境禁止此操作，您可以下载源码运行体验此功能"));
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                // 设置跨域,否则因为拦截了,容器内配置的跨域则无法生效!
                response.addHeader("Access-Control-Allow-Credentials","true");
                response.addHeader("Access-Control-Allow-Origin","*");
                response.addHeader("Access-Control-Allow-Headers","*");
                response.addHeader("Access-Control-Allow-Methods","*");
                response.getWriter().print(toJSONString(r));
            } else {
                // 放行
                filterChain.doFilter(request, response);
            }
        } else {
            // 放行
            filterChain.doFilter(request, response);
        }
    }
}
