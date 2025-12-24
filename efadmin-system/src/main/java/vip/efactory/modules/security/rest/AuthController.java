package vip.efactory.modules.security.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.efactory.annotation.AnonymousAccess;
import vip.efactory.aop.log.Log;
import vip.efactory.common.base.utils.R;
import vip.efactory.exception.BadRequestException;
import vip.efactory.modules.security.config.SecurityProperties;
import vip.efactory.modules.security.security.TokenProvider;
import vip.efactory.modules.security.security.vo.AuthUser;
import vip.efactory.modules.security.security.vo.JwtUser;
import vip.efactory.modules.security.service.OnlineUserService;
import vip.efactory.utils.RedisUtils;
import vip.efactory.utils.SecurityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * 授权、根据token获取用户详细信息
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "系统：系统授权接口") // 替换@Api为@Tag
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class AuthController {

    @Value("${loginCode.expiration}")
    private Long expiration;
    @Value("${rsa.private_key}")
    private String privateKey;
    @Value("${single.login:false}")
    private Boolean singleLogin;

    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private final UserDetailsService userDetailsService;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(SecurityProperties properties, RedisUtils redisUtils, UserDetailsService userDetailsService, OnlineUserService onlineUserService, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.properties = properties;
        this.redisUtils = redisUtils;
        this.userDetailsService = userDetailsService;
        this.onlineUserService = onlineUserService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Log("用户登录")
    @Operation(summary = "登录授权") // 替换@ApiOperation
    @AnonymousAccess
    @PostMapping(value = "/login")
    public R login(@Validated @RequestBody AuthUser authUser, HttpServletRequest request) {
        // 密码解密
        RSA rsa = new RSA(privateKey, null);
        String password = new String(rsa.decrypt(authUser.getPassword(), KeyType.PrivateKey));
        // 查询验证码
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());
        if (isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUser, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<>(2);
        authInfo.put("token", properties.getTokenStartWith() + token);
        authInfo.put("user", jwtUser);

        if (singleLogin) {
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(authUser.getUsername(), token);
        }
        return R.ok(authInfo);
    }

    @Operation(summary = "获取用户信息") // 替换@ApiOperation
    @GetMapping(value = "/info")
    public R getUserInfo() {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return R.ok(jwtUser);
    }

    @AnonymousAccess
    @Operation(summary = "获取验证码") // 替换@ApiOperation
    @GetMapping(value = "/code")
    public R getCode() {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        // 保存
        redisUtils.set(uuid, result, expiration, TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<>(2);
        imgResult.put("img", captcha.toBase64());
        imgResult.put("uuid", uuid);

        return R.ok(imgResult);
    }

    @Operation(summary = "退出登录") // 替换@ApiOperation
    @AnonymousAccess
    @DeleteMapping(value = "/logout")
    public R logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return R.ok();
    }
}