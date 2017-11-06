package com.damon.redis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2017/10/23 19:11
 */
public class RedisLockTest {

    Logger logger = LoggerFactory.getLogger(RedisLock.class);

//    private KeywordExpansionService service;

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Test
    public void test() throws Exception {
        String keywordName = "alibaba";
        for (int i = 0; i < 5; i++) {
            logger.info("index = {}", i);
            executorService.submit(() -> {
//                service.test(new String[]{keywordName, keywordName}, keywordName)
            });
        }
        executorService.shutdown();
        while (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
            logger.info("service not stop");
            System.out.println();
        }
        logger.info("all thread complete");
    }

    @Test
    public void testTry() throws Exception {
        String keywordName = "alibaba";
        for (int i = 0; i < 5; i++) {
            logger.info("index = {}", i);
            executorService.submit(() -> {
//                service.testTry(new String[]{keywordName, keywordName}, keywordName)
            });
        }
    }

}