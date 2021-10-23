package vip.efactory.config;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import javax.annotation.PostConstruct;

/**
 * 国际化文件的配置信息,这个配置类如果不需要自己指定特殊的国际化文件名则不需要
 * 因为依赖的国际化组件,已经包含常见的bansenames了
 * @author dusuanyun
 */
@Configuration
@AllArgsConstructor
public class LocaleConfig {
    private final MessageSource messageSource;

    @PostConstruct
    public void addBaseName() {
        if (messageSource instanceof ReloadableResourceBundleMessageSource) {
            // 注意此处用的是add的方法而不是set的方法,用set可能会覆盖依赖的国际化组件的默认值,导致出错!
            ((ReloadableResourceBundleMessageSource) messageSource).addBasenames("classpath:i18n/messages_ui");
        }
    }
}
