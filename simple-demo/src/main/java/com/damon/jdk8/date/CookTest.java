package com.damon.jdk8.date;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 功能：
 *
 * @author zhoujiwei@idvert.com
 * @since 2018/3/5 14:46
 */
public class CookTest {

    public static Date plusDays(Date date, int days) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        localDateTime = localDateTime.plusDays(days);
        ZonedDateTime zdt = localDateTime.atZone(zone);
        return Date.from(zdt.toInstant());
    }

    public static Date plusDays2(Date date, int days) {
        Instant instant = date.toInstant();
        instant = instant.plus(days, ChronoUnit.DAYS);
        return Date.from(instant);
    }

    public static int offsetDaysFromNow2(Date toDate) {
        LocalDate localDate = LocalDate.now();
        Instant instant = toDate.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate toLocalDate = localDateTime.toLocalDate();
        java.time.Period period = java.time.Period.between(toLocalDate, localDate);
        return period.getDays();
    }

    public static int offsetDaysFromNow3(Date toDate) {
        return (int) Duration.between(toDate.toInstant(), Instant.now()).toDays();
    }

    public static void main(String[] args) {
        Date payDate = new Date(117, 9, 27, 12, 12, 12);
        System.out.println(payDate);
        System.out.println(plusDays(payDate,10));
        System.out.println(plusDays2(payDate,10));
        System.out.println("--------------------");


        Date day = new Date(118, 2, 3, 14, 12, 12);
        System.out.println(day);
        System.out.println("new1:"+offsetDaysFromNow2(day));
        System.out.println("new2:"+offsetDaysFromNow3(day));
        System.out.println("--------------------");
    }
}
