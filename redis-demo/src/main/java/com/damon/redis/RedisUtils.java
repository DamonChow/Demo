package com.damon.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/12/30 11:19
 */
public class RedisUtils {

    public static JedisCluster getRedisCluster() {
        Set<HostAndPort> redisClusterNodes = new HashSet<HostAndPort>();
        redisClusterNodes.add(new HostAndPort("10.1.6.67", 6379));
        redisClusterNodes.add(new HostAndPort("10.1.6.68", 6379));
        redisClusterNodes.add(new HostAndPort("10.1.6.69", 6379));
        return new JedisCluster(redisClusterNodes);
    }

    public static Jedis getRedisFromLocal() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
        return pool.getResource();
    }

    public static Jedis getRedisFromAAA() {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "10.169.12.62", 6379);
        return pool.getResource();
    }

}