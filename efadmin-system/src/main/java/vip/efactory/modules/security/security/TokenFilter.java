package vip.efactory.modules.security.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vip.efactory.modules.security.config.SecurityProperties;
import vip.efactory.modules.security.service.OnlineUserService;
import vip.efactory.modules.security.security.vo.OnlineUser;

import java.io.IOException;

@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final SecurityProperties securityProperties;
    private final OnlineUserService onlineUserService;

    public TokenFilter(TokenProvider tokenProvider,
                       SecurityProperties securityProperties,
                       OnlineUserService onlineUserService) {
        this.tokenProvider = tokenProvider;
        this.securityProperties = securityProperties;
        this.onlineUserService = onlineUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 解析Token
        String token = resolveToken(request);
        String requestUri = request.getRequestURI();

        OnlineUser onlineUser = null;
        try {
            // 查询在线用户
            if (StringUtils.hasText(token)) {
                onlineUser = onlineUserService.getOne(securityProperties.getOnlineKey() + token);
            }
        } catch (ExpiredJwtException e) {
            log.error("Token已过期: {}", e.getMessage());
        }

        // 验证Token有效性并设置认证信息
        if (onlineUser != null && StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("用户 [{}] 认证成功，访问URI: {}", authentication.getName(), requestUri);
        } else {
            log.debug("无效Token或未登录，访问URI: {}", requestUri);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 从请求头解析Token
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(securityProperties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(securityProperties.getTokenStartWith())) {
            // 截取Token前缀后的部分
            return bearerToken.substring(securityProperties.getTokenStartWith().length()).trim();
        }
        return null;
    }
}