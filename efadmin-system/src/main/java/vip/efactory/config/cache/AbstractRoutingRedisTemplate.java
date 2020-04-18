package vip.efactory.config.cache;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Closeable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author James Wu 2018-3-22
 * <p>
 * Abstract {@link org.springframework.data.redis.core.RedisTemplate} implementation that routes {@link #getConnectionFactory()}
 * calls to one of various target RedisTemplate based on a lookup key. The latter is usually
 * (but not necessarily) determined through some thread-bound transaction context.
 */
public abstract class AbstractRoutingRedisTemplate<K, V> extends RedisTemplate<K, V> {

    private Map<Integer, RedisTemplate<K, V>> redisTemplates = new HashMap<>();

    @Override
    public RedisConnectionFactory getConnectionFactory() {
        return this.determineTargetRedisTemplate().getConnectionFactory();
    }

    @Override
    public <T> T execute(RedisCallback<T> action) {
        return this.determineTargetRedisTemplate().execute(action);
    }

    @Override
    public <T> T execute(SessionCallback<T> session) {
        return this.determineTargetRedisTemplate().execute(session);
    }

    @Override
    public List<Object> executePipelined(RedisCallback<?> action) {
        return this.determineTargetRedisTemplate().executePipelined(action);
    }

    @Override
    public List<Object> executePipelined(RedisCallback<?> action, RedisSerializer<?> resultSerializer) {
        return this.determineTargetRedisTemplate().executePipelined(action, resultSerializer);
    }

    @Override
    public List<Object> executePipelined(SessionCallback<?> session) {
        return this.determineTargetRedisTemplate().executePipelined(session);
    }

    @Override
    public List<Object> executePipelined(SessionCallback<?> session, RedisSerializer<?> resultSerializer) {
        return this.determineTargetRedisTemplate().executePipelined(session, resultSerializer);
    }

    @Override
    public <T> T execute(RedisScript<T> script, List<K> keys, Object... args) {
        return this.determineTargetRedisTemplate().execute(script, keys, args);
    }

    @Override
    public <T> T execute(RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer,
                         List<K> keys, Object... args) {
        return this.determineTargetRedisTemplate().execute(script, argsSerializer, resultSerializer, keys, args);
    }

    @Override
    public <T extends Closeable> T executeWithStickyConnection(RedisCallback<T> callback) {
        return this.determineTargetRedisTemplate().executeWithStickyConnection(callback);
    }

    @Override
    public Boolean hasKey(K key) {
        return this.determineTargetRedisTemplate().hasKey(key);
    }

    @Override
    public Boolean delete(K key) {
        return this.determineTargetRedisTemplate().delete(key);
    }

    @Override
    public Long delete(Collection<K> keys) {
        return this.determineTargetRedisTemplate().delete(keys);
    }

    @Override
    public DataType type(K key) {
        return this.determineTargetRedisTemplate().type(key);
    }

    @Override
    public Set<K> keys(K pattern) {
        return this.determineTargetRedisTemplate().keys(pattern);
    }

    @Override
    public K randomKey() {
        return this.determineTargetRedisTemplate().randomKey();
    }

    @Override
    public void rename(K oldKey, K newKey) {
        this.determineTargetRedisTemplate().rename(oldKey, newKey);

    }

    @Override
    public Boolean renameIfAbsent(K oldKey, K newKey) {
        return this.determineTargetRedisTemplate().renameIfAbsent(oldKey, newKey);
    }

    @Override
    public Boolean expire(K key, long timeout, TimeUnit unit) {
        return this.determineTargetRedisTemplate().expire(key, timeout, unit);
    }

    @Override
    public Boolean expireAt(K key, Date date) {
        return this.determineTargetRedisTemplate().expireAt(key, date);
    }

    @Override
    public Boolean persist(K key) {
        return this.determineTargetRedisTemplate().persist(key);
    }

    @Override
    public Boolean move(K key, int dbIndex) {
        return this.determineTargetRedisTemplate().move(key, dbIndex);
    }

    @Override
    public byte[] dump(K key) {
        return this.determineTargetRedisTemplate().dump(key);
    }

    @Override
    public void restore(K key, byte[] value, long timeToLive, TimeUnit unit) {
        this.determineTargetRedisTemplate().restore(key, value, timeToLive, unit);
    }

    @Override
    public Long getExpire(K key) {
        return this.determineTargetRedisTemplate().getExpire(key);
    }

    @Override
    public Long getExpire(K key, TimeUnit timeUnit) {
        return this.determineTargetRedisTemplate().getExpire(key, timeUnit);
    }

    @Override
    public List<V> sort(SortQuery<K> query) {
        return this.determineTargetRedisTemplate().sort(query);
    }

    @Override
    public <T> List<T> sort(SortQuery<K> query, RedisSerializer<T> resultSerializer) {
        return this.determineTargetRedisTemplate().sort(query, resultSerializer);
    }

    @Override
    public <T> List<T> sort(SortQuery<K> query, BulkMapper<T, V> bulkMapper) {
        return this.determineTargetRedisTemplate().sort(query, bulkMapper);
    }

    @Override
    public <T, S> List<T> sort(SortQuery<K> query, BulkMapper<T, S> bulkMapper, RedisSerializer<S> resultSerializer) {
        return this.determineTargetRedisTemplate().sort(query, bulkMapper, resultSerializer);
    }

    @Override
    public Long sort(SortQuery<K> query, K storeKey) {
        return this.determineTargetRedisTemplate().sort(query, storeKey);
    }

    @Override
    public void watch(K key) {
        this.determineTargetRedisTemplate().watch(key);
    }

    @Override
    public void watch(Collection<K> keys) {
        this.determineTargetRedisTemplate().watch(keys);
    }

    @Override
    public void unwatch() {
        this.determineTargetRedisTemplate().unwatch();
    }

    @Override
    public void multi() {
        this.determineTargetRedisTemplate().multi();
    }

    @Override
    public void discard() {
        this.determineTargetRedisTemplate().discard();
    }

    @Override
    public List<Object> exec() {
        return this.determineTargetRedisTemplate().exec();
    }

    @Override
    public List<Object> exec(RedisSerializer<?> valueSerializer) {
        return this.determineTargetRedisTemplate().exec(valueSerializer);
    }

    @Override
    public List<RedisClientInfo> getClientList() {
        return this.determineTargetRedisTemplate().getClientList();
    }

    @Override
    public void killClient(String host, int port) {
        this.determineTargetRedisTemplate().killClient(host, port);
    }

    @Override
    public void slaveOf(String host, int port) {
        this.determineTargetRedisTemplate().slaveOf(host, port);
    }

    @Override
    public void slaveOfNoOne() {
        this.determineTargetRedisTemplate().slaveOfNoOne();
    }

    @Override
    public void convertAndSend(String destination, Object message) {
        this.determineTargetRedisTemplate().convertAndSend(destination, message);
    }

    @Override
    public ValueOperations<K, V> opsForValue() {
        return this.determineTargetRedisTemplate().opsForValue();
    }

    @Override
    public BoundValueOperations<K, V> boundValueOps(K key) {
        return this.determineTargetRedisTemplate().boundValueOps(key);
    }

    @Override
    public ListOperations<K, V> opsForList() {
        return this.determineTargetRedisTemplate().opsForList();
    }

    @Override
    public BoundListOperations<K, V> boundListOps(K key) {
        return this.determineTargetRedisTemplate().boundListOps(key);
    }

    @Override
    public SetOperations<K, V> opsForSet() {
        return this.determineTargetRedisTemplate().opsForSet();
    }

    @Override
    public BoundSetOperations<K, V> boundSetOps(K key) {
        return this.determineTargetRedisTemplate().boundSetOps(key);
    }

    @Override
    public ZSetOperations<K, V> opsForZSet() {
        return this.determineTargetRedisTemplate().opsForZSet();
    }

    @Override
    public HyperLogLogOperations<K, V> opsForHyperLogLog() {
        return this.determineTargetRedisTemplate().opsForHyperLogLog();
    }

    @Override
    public BoundZSetOperations<K, V> boundZSetOps(K key) {
        return this.determineTargetRedisTemplate().boundZSetOps(key);
    }

    @Override
    public <HK, HV> HashOperations<K, HK, HV> opsForHash() {
        return this.determineTargetRedisTemplate().opsForHash();
    }

    @Override
    public <HK, HV> BoundHashOperations<K, HK, HV> boundHashOps(K key) {
        return this.determineTargetRedisTemplate().boundHashOps(key);
    }

    @Override
    public GeoOperations<K, V> opsForGeo() {
        return this.determineTargetRedisTemplate().opsForGeo();
    }

    @Override
    public BoundGeoOperations<K, V> boundGeoOps(K key) {
        return this.determineTargetRedisTemplate().boundGeoOps(key);
    }

    @Override
    public ClusterOperations<K, V> opsForCluster() {
        return this.determineTargetRedisTemplate().opsForCluster();
    }

    @Override
    public RedisSerializer<?> getKeySerializer() {
        return this.determineTargetRedisTemplate().getKeySerializer();
    }

    @Override
    public RedisSerializer<?> getValueSerializer() {
        return this.determineTargetRedisTemplate().getValueSerializer();
    }

    @Override
    public RedisSerializer<?> getHashKeySerializer() {
        return this.determineTargetRedisTemplate().getHashKeySerializer();
    }

    @Override
    public RedisSerializer<?> getHashValueSerializer() {
        return this.determineTargetRedisTemplate().getHashValueSerializer();
    }

    protected RedisTemplate<K, V> determineTargetRedisTemplate() {
        Integer lookupKey = determineCurrentLookupKey();
        RedisTemplate<K, V> template = this.redisTemplates.get(lookupKey);
        if (template == null) {
            template = this.createRedisTemplateOnMissing(lookupKey);
            this.redisTemplates.put(lookupKey, template);
        }
        return template;
    }

    protected abstract RedisTemplate<K, V> createRedisTemplateOnMissing(Integer lookupKey);

    protected abstract Integer determineCurrentLookupKey();
}
