package vip.efactory.modules.security.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import vip.efactory.annotation.AnonymousAccess;
import vip.efactory.ejpa.tenant.identifier.TenantHolderFilter;
import vip.efactory.modules.security.security.JwtAccessDeniedHandler;
import vip.efactory.modules.security.security.JwtAuthenticationEntryPoint;
import vip.efactory.modules.security.security.TokenConfigurer;
import vip.efactory.modules.security.security.TokenProvider;
import vip.efactory.modules.security.service.OnlineUserService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final ApplicationContext applicationContext;
    private final SecurityProperties securityProperties;
    private final OnlineUserService onlineUserService;

    public SecurityConfig(TokenProvider tokenProvider,
                          CorsFilter corsFilter,
                          JwtAuthenticationEntryPoint authenticationErrorHandler,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler,
                          ApplicationContext applicationContext,
                          SecurityProperties securityProperties,
                          OnlineUserService onlineUserService) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.applicationContext = applicationContext;
        this.securityProperties = securityProperties;
        this.onlineUserService = onlineUserService;
    }

    @Bean
    public TenantHolderFilter tenantHolderFilter() {
        return new TenantHolderFilter();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        Set<String> anonymousUrls = collectAnonymousUrls();
        http
                // 🔥 修正过滤器顺序：CorsFilter 最优先 → TenantHolderFilter → Spring Security过滤器
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .apply(securityConfigurerAdapter()) // 先执行TokenConfigurer，添加TokenFilter
                .and()
                .addFilterBefore(tenantHolderFilter(), UsernamePasswordAuthenticationFilter.class)

                // 基础配置
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // 启用CORS，配合CorsFilter

                // 异常处理
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationErrorHandler)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🔥 修正授权规则：所有permitAll集中在anyRequest之前，且只留一个anyRequest
                .authorizeHttpRequests(authorize -> authorize
                        // 1. OPTIONS请求优先放行（跨域预检）
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 2. 静态资源放行
                        .requestMatchers(new AntPathRequestMatcher("/**/*.html")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**/*.css")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**/*.js")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/webSocket/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/avatar/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/file/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/druid/**")).permitAll()

                        // 3. Swagger放行
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()

                        // 5. 匿名URL放行
                        .requestMatchers(anonymousUrls.stream().map(AntPathRequestMatcher::new).toArray(org.springframework.security.web.util.matcher.RequestMatcher[]::new)).permitAll()

                        // 6. 最后只留一个anyRequest（所有未匹配的请求需要认证）
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    private TokenConfigurer securityConfigurerAdapter() {
        return new TokenConfigurer(tokenProvider, securityProperties, onlineUserService);
    }

    private Set<String> collectAnonymousUrls() {
        Set<String> anonymousUrls = new HashSet<>();
        try {
            Map<RequestMappingInfo, HandlerMethod> handlerMethodMap =
                    applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();

            for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
                HandlerMethod handler = entry.getValue();
                if (handler.getMethodAnnotation(AnonymousAccess.class) != null) {
                    RequestMappingInfo info = entry.getKey();
                    try {
                        if (info.getPathPatternsCondition() != null && !info.getPathPatternsCondition().getPatterns().isEmpty()) {
                            anonymousUrls.addAll(info.getPathPatternsCondition().getPatterns().stream()
                                    .map(PathPattern::getPatternString).collect(Collectors.toSet()));
                            continue;
                        }
                    } catch (Exception ignore) {}
                    if (info.getPatternsCondition() != null) {
                        anonymousUrls.addAll(info.getPatternsCondition().getPatterns());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("collectAnonymousUrls error: " + e.getMessage());
        }
        return anonymousUrls;
    }
}