package vip.efactory.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import vip.efactory.ejpa.tenant.identifier.TenantConstants;
import vip.efactory.ejpa.tenant.identifier.TenantHolder;

/**
 * JedisConnectionFactory 复制修改而来
 */
@Slf4j
public class XRedisConnectionFactory extends JedisConnectionFactory {

    public XRedisConnectionFactory() {
    }

    @Deprecated
    public XRedisConnectionFactory(JedisShardInfo shardInfo) {
        super(shardInfo);
    }

    public XRedisConnectionFactory(JedisPoolConfig poolConfig) {
        super(poolConfig);
    }

    public XRedisConnectionFactory(RedisSentinelConfiguration sentinelConfig) {
        super(sentinelConfig);
    }

    public XRedisConnectionFactory(RedisSentinelConfiguration sentinelConfig, JedisPoolConfig poolConfig) {
        super(sentinelConfig, poolConfig);
    }

    public XRedisConnectionFactory(RedisClusterConfiguration clusterConfig) {
        super(clusterConfig);
    }

    public XRedisConnectionFactory(RedisClusterConfiguration clusterConfig, JedisPoolConfig poolConfig) {
        super(clusterConfig, poolConfig);
    }

    public XRedisConnectionFactory(RedisStandaloneConfiguration standaloneConfig) {
        super(standaloneConfig);
    }

    public XRedisConnectionFactory(RedisStandaloneConfiguration standaloneConfig, JedisClientConfiguration clientConfig) {
        super(standaloneConfig, clientConfig);
    }

    public XRedisConnectionFactory(RedisSentinelConfiguration sentinelConfig, JedisClientConfiguration clientConfig) {
        super(sentinelConfig, clientConfig);
    }

    public XRedisConnectionFactory(RedisClusterConfiguration clusterConfig, JedisClientConfiguration clientConfig) {
        super(clusterConfig, clientConfig);
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
