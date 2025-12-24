package vip.efactory.modules.security.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vip.efactory.modules.security.config.SecurityProperties;
import vip.efactory.modules.security.service.OnlineUserService;

public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final SecurityProperties securityProperties;
    private final OnlineUserService onlineUserService;

    public TokenConfigurer(TokenProvider tokenProvider,
                           SecurityProperties securityProperties,
                           OnlineUserService onlineUserService) {
        this.tokenProvider = tokenProvider;
        this.securityProperties = securityProperties;
        this.onlineUserService = onlineUserService;
    }

    @Override
    public void configure(HttpSecurity http) {
        TokenFilter tokenFilter = new TokenFilter(tokenProvider, securityProperties, onlineUserService);
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}