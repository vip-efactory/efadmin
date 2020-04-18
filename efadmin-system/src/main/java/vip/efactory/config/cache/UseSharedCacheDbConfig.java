package vip.efactory.config.cache;

import java.lang.annotation.*;

/**
 * 使用默认的redis db 0,通常一些租户间通用的缓存数据可以配置在默认db
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UseSharedCacheDbConfig {
    String value() default "";
}
