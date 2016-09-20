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

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/9/20 13:17
 */
public class SimpleTest {

    protected static Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    ShardedJedisPool pool;

    ShardedJedis resource;

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

    @After
    public void after() {
        pool.returnResourceObject(resource);
        pool.close();
    }
}
