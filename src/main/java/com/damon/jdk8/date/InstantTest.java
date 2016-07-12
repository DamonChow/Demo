package com.damon.jdk8.date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/7/12 10:45
 */
public class InstantTest {

    private Logger logger = LoggerFactory.getLogger(LocalDateTest.class);

    @Test
    public void test1() {
        Instant now = Instant.now();
        logger.info("当前时间戳：{}.", now);


    }
}
