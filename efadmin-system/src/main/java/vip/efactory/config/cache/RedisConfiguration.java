package vip.efactory.config.cache;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.repository.configuration.RedisRepositoriesRegistrar;
import org.springframework.data.redis.repository.support.RedisRepositoryFactoryBean;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConditionalOnProperty("spring.redis.host")
@EnableCaching
@ConditionalOnClass(value = {RedisOperations.class, EnableRedisRepositories.class})
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@Import({RedisRepositoriesRegistrar.class})
@ConditionalOnMissingBean(RedisRepositoryFactoryBean.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfiguration implements WebMvcConfigurer {
//public class RedisConfiguration {

    private String cache_name = "";
    private String expiration = "";
    private long defaultExpiration = 7200;

    public class Cache {
        public int index;
        public String name;
        public long expiration;
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        DynamicRedisTemplate<String, Object> redisTemplate = new DynamicRedisTemplate<String, Object>();
        redisTemplate.setKeySerializer(new GenericToStringSerializer(Object.class));
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer(Object.class));
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

//    @Bean
//    public CacheManager cacheManager(RedisTemplate<String, String> redisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        List<String> cacheNames = new ArrayList<String>();
//        Map<String, Long> cacheExpirations = new HashMap<String, Long>(cacheNames.size(), 1);
//        String[] exps = expiration.split(",");
//        Cache c = new Cache();
//        Optional.ofNullable(cache_name)
//                .ifPresent(cname -> {
//                    c.index = 0;
//                    Arrays.asList(cname.split(","))
//                            .forEach(name -> {
//                                if (name != null && !name.equals("")) {
//                                    cacheNames.add(name);
//                                    c.index = c.index++;
//                                    if (exps[c.index] != null && !exps[c.index].equals("")) {
//                                        cacheExpirations.put(name, Long.valueOf(exps[c.index]));
//                                    }
//                                }
//                            });
//                });
//        cacheManager.setCacheNames(cacheNames);
//        cacheManager.setDefaultExpiration(defaultExpiration);
//        cacheManager.setExpires(cacheExpirations);
//        return cacheManager;
//    }

    /**
     * 多租户解析解析拦截器，用来映射租户对应redis缓存数据库索引
     */
    @Bean
    public RedisTenantInterceptor redisTenantInterceptor() {
        RedisTenantInterceptor redisTenantInterceptor = new RedisTenantInterceptor();
        return redisTenantInterceptor;
    }

    /**
     * 注册Redis租户信息拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(redisTenantInterceptor());
    }

}  
