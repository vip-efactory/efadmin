package vip.efactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.efactory.annotation.AnonymousAccess;

/**
 * @author dusuanyun
 */
@EnableAsync
@EnableJpaAuditing
@EnableMethodSecurity
@RestController
@SpringBootApplication
@ComponentScan(basePackages = {
        "vip.efactory.config",    // 原有配置类
        "vip.efactory.modules",   // 业务组件（UserService 所在包）
        "vip.efactory.service",
        "vip.efactory.utils",     // RedisUtils 所在包
        "vip.efactory.rest"       // 扫描LogController所在的包
})
public class AppRun {

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }


    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory fa = new TomcatServletWebServerFactory();
        fa.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return fa;
    }

    /**
     * 访问首页提示
     *
     * @return /
     */
    @GetMapping("/")
    @AnonymousAccess
    public String index() {
        return "Backend service started successfully";
    }
}
