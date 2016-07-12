package com.damon.jdk8.date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/7/12 11:16
 */
public class LocalDateTest {

    private Logger logger = LoggerFactory.getLogger(LocalDateTest.class);

    @Test
    public void test1() {
        LocalDate now = LocalDate.now();
        logger.info("当前日期:{}", now);
        logger.info("当前年份：{}，月份:{},日期:{}.", new Object[]{now.getYear(), now.getMonth(), now.getDayOfMonth()});
        logger.info("当前年份：{}，月份:{},日期:{}.", new Object[]{now.getYear(), now.getMonthValue(), now.getDayOfMonth()});

        LocalDate testDate = LocalDate.of(2016,07,30);
        logger.info("定义testDate时间:{}", testDate);
        logger.info("testDate时间是否与LocalDate.of(2016,7,30)相等：{}", testDate.equals(LocalDate.of(2016,7,30)));

        MonthDay testMonthDay = MonthDay.of(testDate.getMonthValue(), testDate.getDayOfMonth());
        MonthDay fromMonthDay = MonthDay.from(now);
        logger.info("testMonthDay={}.", testMonthDay);
        logger.info("fromMonthDay={}.", fromMonthDay);
        if (testMonthDay.equals(fromMonthDay)) {
            logger.info("same it..");
        } else {
            logger.info("doesn't same it..");
        }

        LocalDate newDate = now.plus(1l, ChronoUnit.CENTURIES);
        logger.info("日期变更后:{}", newDate);


        LocalDate preYears   = now.minus(1l, ChronoUnit.YEARS);
        LocalDate nextYears = now.plus(1l, ChronoUnit.YEARS);
        logger.info("前一年为:{}", preYears);
        logger.info("后一年为:{}", nextYears);


        Clock clock = Clock.systemUTC();
        logger.info("clock is {}.", clock);

        Period between = Period.between(now, testDate);
        logger.info("{} between {} day with {}.", new Object[]{testDate, between.getDays(), now});
    }
}
