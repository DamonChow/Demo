package com.damon.redis;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/9/20 13:17
 */
public class SimpleTest {

    protected static Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    private Jedis jedis;

    @Before
    public void init() {
        jedis = RedisUtils.getRedisFromLocal();
    }

    @Test
    public void test() {
        jedis.set("a","1");
        logger.info("get a is {}.", jedis.get("a"));
    }

}