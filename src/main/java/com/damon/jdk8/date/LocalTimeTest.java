package com.damon.jdk8.date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/7/12 11:16
 */
public class LocalTimeTest {

    private Logger logger = LoggerFactory.getLogger(LocalTimeTest.class);

    @Test
    public void test1() {
        LocalTime now = LocalTime.now();
        logger.info("当前时间:{}", now);

        LocalTime newTime = now.plusHours(2l);
        logger.info("加上2个小时后的时间:{}", newTime);

        LocalTime newTime2 = now.plus(20l, ChronoUnit.MINUTES);
        logger.info("加上20分钟后的时间:{}", newTime2);

    }
}
