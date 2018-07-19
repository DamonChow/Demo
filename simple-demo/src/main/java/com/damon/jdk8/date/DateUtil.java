package com.damon.jdk8.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018/7/19 11:26
 */
public class DateUtil {

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * Date转LocalDateTime
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 当前时间到第二天24点剩余多少秒
     *
     * @return
     */
    public static long getNextLastSeconds() {
        LocalDateTime max = LocalDate.now().plusDays(1).atTime(LocalTime.MAX);
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), max);
        return seconds;
    }

    /**
     * 当前时间到24点剩余多少秒
     *
     * @return
     */
    public static long getLastSeconds() {
        LocalDateTime max = LocalDate.now().atTime(LocalTime.MAX);
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), max);
        return seconds;
    }

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static Date getStartDayTime() {
        return getStartDayTime(new Date());
    }

    /**
     * 获取当天最晚时间
     *
     * @return
     */
    public static Date getEndDayTime() {
        return getEndDayTime(new Date());
    }

    /**
     * 获取传入时间的那天开始时间
     *
     * @param date 时间
     * @return
     */
    public static Date getStartDayTime(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        LocalDateTime startTime = LocalDateTime.of(toLocalDateTime(date).toLocalDate(),
                LocalTime.MIN);
        return toDate(startTime);
    }

    /**
     * 获取传入时间的那天结束时间
     *
     * @param date 时间
     * @return
     */
    public static Date getEndDayTime(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        LocalDateTime todayStart = LocalDateTime.of(toLocalDateTime(date).toLocalDate(),
                LocalTime.MAX);
        return toDate(todayStart);
    }

    /**
     * 添加天数
     *
     * @param date
     * @param days
     * @return
     */
    public static Date plusDay(Date date, int days) {
        if (Objects.isNull(date)) {
            return null;
        }

        LocalDateTime localDateTime = toLocalDateTime(date);
        LocalDateTime plusDateTime = localDateTime.plusDays(days);
        return toDate(plusDateTime);
    }

    /**
     * startDate是否在endDate之前
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isBefore(Date startDate, Date endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            return false;
        }

        LocalDateTime startDateTime = toLocalDateTime(startDate);
        LocalDateTime endDateTime = toLocalDateTime(endDate);
        return startDateTime.isBefore(endDateTime);
    }

    /**
     * 计算当前日期与{@code endDate}的间隔天数
     *
     * @param date
     * @return 间隔天数
     */
    public static long getAfterDays(Date date) {
        if (Objects.isNull(date)) {
            return 0l;
        }
        return getDays(new Date(), date);
    }

    /**
     * 计算{@code endDate}与当前日期的间隔天数
     *
     * @param date
     * @return 间隔天数
     */
    public static long getBeforeDays(Date date) {
        if (Objects.isNull(date)) {
            return 0l;
        }
        return getDays(date, new Date());
    }

    /**
     * 计算日期{@code startDate}与{@code endDate}的间隔天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDays(Date startDate, Date endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            return 0L;
        }

        return toLocalDateTime(startDate).until(toLocalDateTime(endDate), ChronoUnit.DAYS);
    }

    /**
     * 计算当前日期与{@code endDate}的间隔秒数
     *
     * @param date
     * @return 间隔秒数
     */
    public static long getAfterSeconds(Date date) {
        if (Objects.isNull(date)) {
            return 0l;
        }
        return getSeconds(new Date(), date);
    }

    /**
     * 计算{@code endDate}与当前日期的间隔秒数
     *
     * @param date
     * @return 间隔秒数
     */
    public static long getBeforeSeconds(Date date) {
        if (Objects.isNull(date)) {
            return 0l;
        }
        return getSeconds(date, new Date());
    }

    /**
     * 计算日期{@code startDate}与{@code endDate}的间隔秒数
     *
     * @param startDate
     * @param endDate
     * @return 间隔秒数
     */
    public static long getSeconds(Date startDate, Date endDate) {
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
            return 0L;
        }

        return toLocalDateTime(startDate).until(toLocalDateTime(endDate), ChronoUnit.SECONDS);
    }

}
