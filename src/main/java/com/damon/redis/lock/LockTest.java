package com.damon.redis.lock;

import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RKeys;
import org.redisson.core.RLock;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/4/25 14:04
 */
public class LockTest {

    public static void main(String[] args) throws Exception {
        RedissonClient redissonClient = Redisson.create(Config.fromYAML("10.1.0.208:6379"));

        RKeys keys = redissonClient.getKeys();
        System.out.println(keys);
        /*lock.lock();
        try {
            System.out.println("hagogrgr");
        }
        finally {
            lock.unlock();
        }*/

        redissonClient.shutdown();
    }
}
