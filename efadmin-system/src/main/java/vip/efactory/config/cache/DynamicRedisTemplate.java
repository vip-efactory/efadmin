package vip.efactory.config.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPoolConfig;
import vip.efactory.utils.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author James Wu 2018-3-22
 * <p>
 * switch Redis template based on contextLocal's dbIndex
 */
@Slf4j
public class DynamicRedisTemplate<K, V> extends AbstractRoutingRedisTemplate<K, V> {
    private static final ThreadLocal<Integer> contextLocal = new TransmittableThreadLocal<>();

    public static void setCurrentDbIndex(Integer dbIndex) {
        contextLocal.set(dbIndex);
    }

    public static Integer getCurrentDbIndex() {
        return contextLocal.get();
    }

    public static void remove() {
        contextLocal.remove();
    }

    @Autowired
    private Environment env;

    @Override
    protected Integer determineCurrentLookupKey() {
        Integer dbIndex;
        if (DynamicRedisTemplate.getCurrentDbIndex() == null) {
            dbIndex = 0;
        } else {
            dbIndex = DynamicRedisTemplate.getCurrentDbIndex();
        }
        return dbIndex;
    }

    @Override
    protected RedisTemplate<K, V> createRedisTemplateOnMissing(Integer lookupKey) {
        RedisTemplate<K, V> template = new RedisTemplate<K, V>();
        template.setConnectionFactory(this.createConnectionFactory(lookupKey));
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // 指定受信任的包名，防止远程漏洞
        ParserConfig.getGlobalInstance().addAccept("vip.efactory");
        template.setValueSerializer(new FastJsonRedisSerializer(Object.class));
        template.setHashValueSerializer(new FastJsonRedisSerializer(Object.class));

        template.afterPropertiesSet();
        log.debug("【redis路由器】create template for database:" + lookupKey);
        return template;
    }

    private RedisClusterConfiguration getClusterConfiguration() {
        Map<String, Object> source = new HashMap<String, Object>();
        String clusterNodes = env.getProperty("spring.redis.cluster.nodes");
        source.put("spring.redis.cluster.nodes", clusterNodes);
        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
    }

    private JedisConnectionFactory createConnectionFactory(Integer database) {
        JedisConnectionFactory redisConnectionFactory = null;
        String clusterEnable = env.getProperty("spring.redis.cluster.enable");
        // 集群配置
        if (clusterEnable != null && clusterEnable.equals("true")) {
            redisConnectionFactory = new JedisConnectionFactory(getClusterConfiguration());
            redisConnectionFactory.setDatabase(database);
            String clusterPassword = env.getProperty("spring.redis.cluster.password");
            redisConnectionFactory.setPassword(clusterPassword);
        } else {
            // 非集群配置
            redisConnectionFactory = new JedisConnectionFactory();
            redisConnectionFactory.setDatabase(database);
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

}

/**
 * Value 序列化
 *
 * @author /
 * @param <T>
 */
 class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

    private Class<T> clazz;

    FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, StandardCharsets.UTF_8);
        return JSON.parseObject(str, clazz);
    }

}

/**
 * 重写序列化器
 *
 * @author /
 */
class StringRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    StringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    private StringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(Object object) {
        String string = JSON.toJSONString(object);
        if (StringUtils.isBlank(string)) {
            return null;
        }
        string = string.replace("\"", "");
        return string.getBytes(charset);
    }
}
