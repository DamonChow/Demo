package com.damon.redis.redisson.lock;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/4/25 14:04
 */
public class LockTest {

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.useSingleServer().setAddress("10.1.0.208:6379");
        RedissonClient redisson = Redisson.create(config);

        RKeys keys = redisson.getKeys();
        System.out.println(keys);
        RLock lock = redisson.getLock("anyLock");
        try {
            if (!lock.tryLock(100, 10, TimeUnit.SECONDS)) {
            System.out.println("not get lock.");
            return;
            }
            System.out.println("was lock.");
        } finally {
            lock.unlock();
        }

        redisson.shutdown();
    }
}
