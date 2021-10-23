package vip.efactory.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import vip.efactory.utils.SecurityUtils;

import java.util.Optional;

/**
 * Description: 这是当前用户的监听器,用来更新用户操作时,记录到创建人或者更新人的字段
 * String 存储用户名或者工号等,不建议存储用户id,一旦这个用户被删,将无从查起!
 *
 * @author dbdu
 */
@Configuration
@Slf4j
public class UserIDAuditorBean implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // 此处是可以用户名或者工号,默认值为System
        String username = "System";
        try {
            username = SecurityUtils.getUsername();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Optional.ofNullable(username);
    }
}
