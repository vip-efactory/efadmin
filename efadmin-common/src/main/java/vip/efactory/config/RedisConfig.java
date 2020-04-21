package vip.efactory.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import redis.clients.jedis.JedisPoolConfig;
import vip.efactory.config.cache.XRedisConnectionFactory;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zheng Jie
 * @date 2018-11-24
 */
@Slf4j
@Configuration
@EnableCaching
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private Environment env;

    /**
     *  设置 redis 数据默认过期时间，默认2小时
     *  设置@cacheable 序列化方式
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())).entryTtl(Duration.ofHours(2));
        return configuration;
    }
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setKeySerializer(new GenericToStringSerializer(Object.class));
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer(Object.class));
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    private RedisClusterConfiguration getClusterConfiguration() {
        Map<String, Object> source = new HashMap<String, Object>();
        String clusterNodes = env.getProperty("spring.redis.cluster.nodes");
        source.put("spring.redis.cluster.nodes", clusterNodes);
        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        XRedisConnectionFactory redisConnectionFactory = null;
        String clusterEnable = env.getProperty("spring.redis.cluster.enable");
        // 集群配置
        if (clusterEnable != null && clusterEnable.equals("true")) {
            redisConnectionFactory = new XRedisConnectionFactory(getClusterConfiguration());
            redisConnectionFactory.setDatabase(TenantHolder.getTenantId().intValue());
            String clusterPassword = env.getProperty("spring.redis.cluster.password");
            redisConnectionFactory.setPassword(clusterPassword);
        } else {
            // 非集群配置
            redisConnectionFactory = new XRedisConnectionFactory();
            redisConnectionFactory.setDatabase(TenantHolder.getTenantId().intValue());
            String host = env.getProperty("spring.redis.host");
            redisConnectionFactory.setHostName(host);
            String port = env.getProperty("spring.redis.port");
            redisConnectionFactory.setPort(Integer.parseInt(port));
            String password = env.getProperty("spring.redis.password");
            redisConnectionFactory.setPassword(password);

        }

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxWaitMillis(1);
        jedisPoolConfig.setMinIdle(0);
        redisConnectionFactory.setPoolConfig(jedisPoolConfig);
        redisConnectionFactory.afterPropertiesSet();
        return redisConnectionFactory;
    }


//    @SuppressWarnings("all")
//    @Bean(name = "redisTemplate")
//    @ConditionalOnMissingBean(name = "redisTemplate")
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        //序列化
//        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
//        // value值的序列化采用fastJsonRedisSerializer
//        template.setValueSerializer(fastJsonRedisSerializer);
//        template.setHashValueSerializer(fastJsonRedisSerializer);
//        // 全局开启AutoType，这里方便开发，使用全局的方式
//        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
//        // 建议使用这种方式，小范围指定白名单
//        // ParserConfig.getGlobalInstance().addAccept("me.zhengjie.domain");
//        // key的序列化采用StringRedisSerializer
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setConnectionFactory(redisConnectionFactory);
//        return template;
//    }

    /**
     * 自定义缓存key生成策略，默认将使用该策略
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            Map<String,Object> container = new HashMap<>(3);
            Class<?> targetClassClass = target.getClass();
            // 类地址
            container.put("class",targetClassClass.toGenericString());
            // 方法名称
            container.put("methodName",method.getName());
            // 包名称
            container.put("package",targetClassClass.getPackage());
            // 参数列表
            for (int i = 0; i < params.length; i++) {
                container.put(String.valueOf(i),params[i]);
            }
            // 转为JSON字符串
            String jsonString = JSON.toJSONString(container);
            // 做SHA256 Hash计算，得到一个SHA256摘要作为Key
            return DigestUtils.sha256Hex(jsonString);
        };
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
    }

}

///**
// * Value 序列化
// *
// * @author /
// * @param <T>
// */
// class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
//
//    private Class<T> clazz;
//
//    FastJsonRedisSerializer(Class<T> clazz) {
//        super();
//        this.clazz = clazz;
//    }
//
//    @Override
//    public byte[] serialize(T t) {
//        if (t == null) {
//            return new byte[0];
//        }
//        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);
//    }
//
//    @Override
//    public T deserialize(byte[] bytes) {
//        if (bytes == null || bytes.length <= 0) {
//            return null;
//        }
//        String str = new String(bytes, StandardCharsets.UTF_8);
//        return JSON.parseObject(str, clazz);
//    }
//
//}
//
///**
// * 重写序列化器
// *
// * @author /
// */
//class StringRedisSerializer implements RedisSerializer<Object> {
//
//    private final Charset charset;
//
//    StringRedisSerializer() {
//        this(StandardCharsets.UTF_8);
//    }
//
//    private StringRedisSerializer(Charset charset) {
//        Assert.notNull(charset, "Charset must not be null!");
//        this.charset = charset;
//    }
//
//    @Override
//    public String deserialize(byte[] bytes) {
//        return (bytes == null ? null : new String(bytes, charset));
//    }
//
//    @Override
//    public byte[] serialize(Object object) {
//        String string = JSON.toJSONString(object);
//        if (StringUtils.isBlank(string)) {
//            return null;
//        }
//        string = string.replace("\"", "");
//        return string.getBytes(charset);
//    }
//}
