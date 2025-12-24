package vip.efactory.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import vip.efactory.ejpa.tenant.identifier.TenantConstants;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;

/**
 * Redis 连接工厂：使用 Lettuce 实现
 */
@Slf4j
public class XRedisConnectionFactory extends LettuceConnectionFactory {

    public XRedisConnectionFactory(RedisStandaloneConfiguration standaloneConfiguration, JedisClientConfiguration jedisClientConfiguration) {
        super();
    }

    public XRedisConnectionFactory(RedisStandaloneConfiguration standaloneConfig) {
        super(standaloneConfig);
    }

    public XRedisConnectionFactory(RedisSentinelConfiguration sentinelConfig) {
        super(sentinelConfig);
    }

    public XRedisConnectionFactory(RedisClusterConfiguration clusterConfig) {
        super(clusterConfig);
    }

    /**
     * 重写的方法
     * 返回redis数据库的索引，例如：0,1,2,3..等
     *
     * @return the database index.
     */
    @Override
    public int getDatabase() {
        int dbIndex;
        if (TenantHolder.getTenantId().longValue() == TenantConstants.DEFAULT_TENANT_ID.longValue()) {
            dbIndex = super.getDatabase();
        } else {
            dbIndex = TenantHolder.getTenantId().intValue();
        }
        log.debug("XRedisConnectionFactory 选择的redis数据库是{}", dbIndex);
        return dbIndex;
    }
}
