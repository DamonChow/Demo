package com.damon.redis.jedis;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Damon on 2017/5/11.
 */
public class SortSetTest {

    protected static Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    ShardedJedisPool pool;

    ShardedJedis jedis;

    @Before
    public void init() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-application.xml");
        pool = ctx.getBean("shardedJedisPool", ShardedJedisPool.class);
        jedis = pool.getResource();
    }

    @Test
    public void test() {
        String key = "mostUsedLanguages";
        //We could add more than one value in one calling
        Map<String, Double> scoreMembers = new HashMap<String, Double>();
        scoreMembers.put("Python", 90d);
        scoreMembers.put("Javascript", 80d);
        jedis.zadd(key,100,"Java");//ZADD
        jedis.zadd(key, scoreMembers);

        //We could get the score for a member
        logger.info("Number of Java users:" + jedis.zscore(key, "Java"));
        //We could get the number of elements on the set
        logger.info("Number of elements:" + jedis.zcard(key));//ZCARD
        //get all the elements sorted from bottom to top
        logger.info("zrange of elements from bottom to top:" + jedis.zrange(key, 0, -1));//zrange
        //get all the elements sorted from top to bottom
        logger.info("zrevrange of elements from top to bottom:" + jedis.zrevrange(key, 0, -1));//zrevrange
        //We could get the elements with the associated score
        Set<Tuple> elements = jedis.zrevrangeWithScores(key, 0, -1);
        for(Tuple tuple: elements){
            logger.info(tuple.getElement() + "-" + tuple.getScore());
        }

        //We can increment a score for a element using ZINCRBY
        logger.info("Score before zincrby:" + jedis.zscore(key, "Python"));
        //Incrementing the element score
        jedis.zincrby(key, 1, "Python");
        logger.info("Score after zincrby:" + jedis.zscore(key, "Python"));
        logger.info("zcount key :" + jedis.zcount(key, 0, -1));
    }
}
