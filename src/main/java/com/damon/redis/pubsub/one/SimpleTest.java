package com.damon.redis.pubsub.one;

import com.damon.redis.RedisUtils;
import com.damon.redis.pubsub.TestPubSub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/3/17 14:31
 */
public class SimpleTest {

    protected static Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    Jedis redis;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    final TestPubSub listener = new TestPubSub();

    @Before
    public void init() {
        redis = RedisUtils.getRedisFromLocal();
    }

    @After
    public void close() {
        redis.close();
    }

    @Test
    public void testIn() {
        logger.info("jian hou = "+redis.decr("test_damon"));
    }

    /**
     * SUBSCRIBE [channel...] 订阅一个匹配的通道
     * PSUBSCRIBE [pattern...] 订阅匹配的通道
     * PUBLISH [channel] [message] 将message推送到channel通道中
     * UNSUBSCRIBE [channel...] 取消订阅消息
     * PUNSUBSCRIBE [pattern ...] 取消匹配的消息订阅
     * web环境中可以编写一个JedisPubSub 继承 @see redis.clients.jedis.JedisPubSub来实现监听
     * Jedis中通过使用 JedisPubSub.UNSUBSCRIBE/PUNSUBSCRIBE 来取消订阅
     */
    @Test
    public void testSubscribe() {

        executor.submit(() -> {
            logger.info("subscribe channelA");
            redis.subscribe(listener, "channelA");
        });

        logger.info("publish start." );
        // 测试发送
        logger.info("publish channelA.test OK : " + redis.publish("channelA.test", "OK"));

        //listener.unsubscribe("channelA.test");
        try {
            Thread.sleep(10000);

            executor.shutdownNow();
            logger.info("executor.shutdownNow");
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.warn("Pool did not terminated");
            }
        } catch (InterruptedException e) {
            logger.error("wrong message: ", e);
            Thread.currentThread().interrupt();
        }
        logger.info("完成subscribe测试");
    }


    /**
     * SUBSCRIBE channelone 订阅一个通道
     * PSUBSCRIBE channel* 订阅一批通道
     * PUBLISH channelone value 将value推送到channelone通道中
     * web环境中可以编写一个Listener 继承 @see redis.clients.jedis.JedisPubSub来实现监听
     */
    @Test
    public void testPsubscribe() {
        executor.submit(() -> {
            logger.info("psubscribe channel*");
            redis.psubscribe(listener, "channel*");
        });

        logger.info("publish channelA.test OK: "  + redis.publish("channelA.test", "OK"));
        logger.info("publish channelB.send_message \"Hello World!\"" + redis.publish("channelB.send_message", "Hello World!"));
        listener.punsubscribe();
        try {
            executor.shutdownNow();
            logger.info("executor.shutdownNow");
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.warn("Pool did not terminated");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        logger.info("完成psubscribe测试");
        logger.info("publish channelA.test OK: " + redis.publish("channelA.test", "OK"));
    }

}
