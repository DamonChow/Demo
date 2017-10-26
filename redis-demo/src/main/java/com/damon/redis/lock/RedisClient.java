package com.damon.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 功能：redis锁
 *
 * @author zhoujiwei@idvert.com
 * @since 2017/10/26 10:47
 */
public class RedisClient {

	@Autowired
    JedisPool jedisPool;

	public boolean acquire(String lockKey, long expireSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.set(lockKey, "1","nx","ex", expireSeconds);
		} catch (Exception e) {
			// 释放redis资源
			jedisPool.returnResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			close(jedis);
		}
		return Objects.equals(result, "OK");
	}

	public boolean tryAcquire(String lockKey, int expireSeconds, int retryTimes, long delay, TimeUnit unit) {
		Jedis jedis = null;
		boolean locked = false;
		try {
			jedis = jedisPool.getResource();

			for (int index = 0; index < retryTimes; index++) {
				String result = jedis.set(lockKey, "1", "nx", "ex", expireSeconds);
				locked = Objects.equals(result, "OK");
				if (locked) {
					return locked;
				}

				unit.sleep(delay);
			}
		} catch (Exception e) {
			// 释放redis资源
			jedisPool.returnResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			close(jedis);
		}
		return locked;
	}

	/**
	 * 返还资源
	 *
	 * @param jedis
	 */
	public void close(Jedis jedis) {
		try {
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			if (jedis.isConnected()) {
				jedis.quit();
				jedis.disconnect();
			}
		}
	}

	public String del(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.del(key) + "";
		} catch (Exception e) {
			// 释放redis资源
			jedisPool.returnResource(jedis);
			e.printStackTrace();
		} finally {
			// 返还到连接池
			close(jedis);
		}
		return result;
	}
}
