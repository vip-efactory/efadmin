package vip.efactory.utils;
import vip.efactory.common.i18n.service.ILocaleMsgSourceService;
import vip.efactory.common.i18n.service.LocaleMsgSourceServiceImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // 标记为配置类，让Spring扫描到
public class I18nBeanConfig {

    /**
     * 手动注册ILocaleMsgSourceService的Bean，解决依赖注入问题
     * @param messageSource Spring上下文自带的MessageSource Bean（默认已存在）
     */
    @Bean
    public ILocaleMsgSourceService localeMsgSourceService(MessageSource messageSource) {
        // 调用构造器创建实例，传入依赖的MessageSource
        return new LocaleMsgSourceServiceImpl(messageSource);
    }
}