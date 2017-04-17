package com.damon.redis.pubsub.keyspace.notification;

import com.damon.redis.RedisUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 功能：过期建通知事件
 * <note>
 *     引用: http://blog.csdn.net/gqtcgq/article/details/50808729
 *     引用: http://redisdoc.com/topic/notification.html
 * </note>
 *
 * <note>
 *     1.redis开启通知：CONFIG SET notify-keyspace-events eX
 *     2.一客服端订阅：subscribe __keyevent@0__:expired
 *     3.另一客服端设置一过期(5秒)：setex test 5 a
 *     4.事件通知时：可以获取通道名称和键名
 * </note>
 *
 * @author Zhoujiwei
 * @since 2016/12/30 11:18
 */
public class SimpleTest {

    Jedis subRedis;

    Jedis pubRedis;

    protected static Logger logger = LoggerFactory.getLogger(com.damon.redis.pubsub.one.SimpleTest.class);

    ExecutorService executor = Executors.newFixedThreadPool(2);

    final Job listener = new Job();

    @Before
    public void init(){
        subRedis = RedisUtils.getRedisFromLocal();
        pubRedis = RedisUtils.getRedisFromLocal();
    }

    @After
    public void close() {
        subRedis.close();
        pubRedis.close();
    }

    @Test
    public void test () {
        executor.submit(() -> {
            logger.info("开始订阅过期事件。");
            subRedis.subscribe(listener, "__keyevent@0__:expired");
            logger.info("订阅结束");
        });

        //设置过期key
        for (int i=0;i<4;i++) {
            final int index = i;
            executor.submit(() -> pubRedis.setex("test_a" + index, 5, "a"));
        }

        //休眠10s
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        logger.info("完成测试");
    }

}
