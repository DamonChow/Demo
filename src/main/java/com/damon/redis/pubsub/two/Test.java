package com.damon.redis.pubsub.two;

import com.damon.redis.pubsub.TestPubSub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/12/30 15:58
 */
public class Test {

    public static final String CHANNEL_NAME = "commonChannel";

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws Exception {

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);

        final Jedis subscriberJedis = jedisPool.getResource();

        final TestPubSub subscriber = new TestPubSub();

        new Thread(() -> {
            try {
                logger.info("Subscribing to \"commonChannel\". This thread will be blocked.");
                subscriberJedis.subscribe(subscriber, CHANNEL_NAME);
                logger.info("Subscription ended.");
            } catch (Exception e) {
                logger.error("Subscribing failed.", e);
            }
        }).start();

        Jedis publisherJedis = jedisPool.getResource();

        new Publisher(publisherJedis, CHANNEL_NAME).start();

        subscriber.unsubscribe();
        jedisPool.returnResource(subscriberJedis);
        jedisPool.returnResource(publisherJedis);
    }
}
