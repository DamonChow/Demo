package com.damon.redis.jedis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/9/20 13:17
 */
public class SimpleTest {

    protected static Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    private ShardedJedisPool pool;

    private ShardedJedis resource;

    @Before
    public void init() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-application.xml");
        pool = ctx.getBean("shardedJedisPool", ShardedJedisPool.class);
        resource = pool.getResource();
    }

    @Test
    public void test() {
        resource.set("a","1");
        logger.info("get a is {}.", resource.get("a"));
    }

    @Test
    public void set() {
        String result = resource.set("key", "value","nx", "ex" , TimeUnit.SECONDS.toSeconds(30));
        logger.info("result is {}.", result);
        logger.info("get key is {}.", resource.get("key"));
    }

    @Test
    public void get() {
        logger.info("get key is {}.", resource.get("key"));
    }

    @Test
    public void testRpushEmpty() {
        byte[][] result = new byte[0][];
        resource.rpush("list".getBytes(), result);
    }

    @After
    public void after() {
        pool.returnResourceObject(resource);
        pool.close();
    }
}
