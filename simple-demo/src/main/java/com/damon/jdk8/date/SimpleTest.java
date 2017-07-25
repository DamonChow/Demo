package com.damon.jdk8.date;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by damon on 2017/7/25.
 */
@Slf4j
public class SimpleTest {

    @Test
    public void localDateTimeDemo() {
        LocalDateTime ldt3 = LocalDateTime.of(2017, 7, 11, 20, 45, 5);
        LocalDateTime ldt2 = LocalDateTime.now();
        log.info("LocalDateTime ldt3={}|LocalDateTime ldt2={}", ldt3, ldt2);

        LocalDateTime ldt = LocalDateTime.of(2017, 7, 11, 20, 45, 5);
        LocalDate ld = ldt.toLocalDate(); //2017-07-11
        LocalTime lt = ldt.toLocalTime(); // 20:45:05
        log.info("LocalDate={}|LocalTime={}", ld, lt);
    }

    @Test
    public void localDateDemo() {
        //表示2017年7月11日
        LocalDate ld = LocalDate.of(2017, 7, 11);
        log.info("ld={}", ld);
        //当前时刻按系统默认时区解读的日期
        LocalDate now = LocalDate.now();
        log.info("now={}", now);
        //LocalDate加上时间，结果为2017-07-11 21:18:39
        LocalDateTime ldt2 = ld.atTime(21, 18, 39);
        log.info("ldt2={}", ldt2);
    }

    @Test
    public void localTimeDemo() {
        //表示21点10分34秒
        LocalTime lt = LocalTime.of(21, 10, 34);
        //当前时刻按系统默认时区解读的时间
        LocalTime time = LocalTime.now();
        //LocalTime加上日期，结果为2016-03-24 20:45:05
        LocalDateTime ldt3 = lt.atDate(LocalDate.of(2016, 3, 24));
    }

    @Test
    public void formatDemo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.of(2016, 8, 18, 14, 20, 45);
        log.info("source is {} , after formatter is {}", ldt, formatter.format(ldt));
    }

    @Test
    public void parseDemo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = "2016-08-18 14:20:45";
        LocalDateTime ldt = LocalDateTime.parse(str, formatter);
        log.info("source is {} , after parse is {}", str, ldt);
    }

    /**
     * 调整时间为下午3点20
     */
    @Test
    public void adjustDateTime1() {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime newDateTime = ldt.withHour(15).withMinute(20).withSecond(0).withNano(0);
        log.info("source is {} , after change is {}", ldt, newDateTime);
        LocalDateTime ldt2 = LocalDateTime.now();
        LocalDateTime newDateTime2 = ldt.toLocalDate().atTime(15, 20);
        log.info("source is {} , after change is {}", ldt2, newDateTime2);
    }

    /**
     * 三小时五分钟后
     */
    @Test
    public void adjustDateTime2() {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime newDateTime = ldt.plusHours(3).plusMinutes(5);
        log.info("source is {} , after change is {}", ldt, newDateTime);
    }

    /**
     * 今天0点
     */
    @Test
    public void adjustDateTime3() {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime newDateTime = ldt.with(ChronoField.MILLI_OF_DAY, 0);
        log.info("source is {} , after change is {}", ldt, newDateTime);
        ldt = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        log.info("source is {} ", ldt);
        ldt = LocalDate.now().atTime(0, 0);
        log.info("source is {} ", ldt);
    }

    /**
     * 下周二上午10点整
     */
    @Test
    public void adjustDateTime4() {
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime newDateTime = ldt.plusWeeks(1).with(ChronoField.DAY_OF_WEEK, 2)
                .with(ChronoField.MILLI_OF_DAY, 0).withHour(10);
        log.info("source is {} , after change is {}", ldt, newDateTime);

        //下周二上午10点
        LocalDate ld2 = LocalDate.now();
        LocalDateTime ldt2 = ld2.with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).atTime(10, 0);
        log.info("source is {} ", ldt2);

        //下一个周二10点
        LocalDate ld3 = LocalDate.now();
        if (!ld3.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            ld3 = ld3.plusWeeks(1);
        }
        LocalDateTime ldt3 = ld3.with(ChronoField.DAY_OF_WEEK, 2).atTime(10, 0);
        log.info("source is {} ", ldt3);
    }

    /**
     * 明天最后一刻
     */
    @Test
    public void adjustDateTime6() {
        LocalDateTime ldt = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MAX);
        log.info("明天最后一刻 is {} ", ldt);
        LocalDateTime ldt2 = LocalTime.MAX.atDate(LocalDate.now().plusDays(1));
        log.info("明天最后一刻 is {} ", ldt2);
    }

    /**
     * 本月最后一天
     */
    @Test
    public void adjustDateTime7() {
        LocalDateTime ldt = LocalDate.now()
                .with(TemporalAdjusters.lastDayOfMonth())
                .atTime(LocalTime.MAX);
        log.info("本月最后一天最后一刻 is {} ", ldt);

        LocalDateTime min = LocalDate.now()
                .with(TemporalAdjusters.lastDayOfMonth())
                .atTime(LocalTime.MIN);
        log.info("本月最后一天最开始一刻 is {} ", min);
    }

    /**
     * 本天剩余多少秒
     */
    @Test
    public void lastSeconds() {
        LocalDateTime max = LocalDate.now().atTime(LocalTime.MAX);
        log.info("max is {}", max);
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), max);
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), max);
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), max);
        log.info("当天剩余{}小时", hours);
        log.info("当天剩余{}分", minutes);
        log.info("当天剩余{}秒", seconds);
    }

    /**
     * 下个月第一个周一的下午5点整
     */
    @Test
    public void adjustDateTime8() {
        LocalDateTime ldt = LocalDate.now()
                .plusMonths(1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))
                .atTime(17, 0);
        log.info("下个月第一个周一的下午5点整 is {}", ldt);
    }

    @Test
    public void periodDemo() {
        LocalDate ld1 = LocalDate.of(2016, 3, 24);
        LocalDate ld2 = LocalDate.of(2017, 7, 12);
        Period period = Period.between(ld1, ld2);
        log.info(period.getYears() + "年"
                + period.getMonths() + "月" + period.getDays() + "天");
    }

    /**
     *  多大年纪
     */
    @Test
    public void ageByBorn() {
        LocalDate born = LocalDate.of(1990, 06, 20);
        int year = Period.between(born, LocalDate.now()).getYears();
        log.info("今年{}岁", year);
    }

    /**
     * 9点后过去多少分
     */
    @Test
    public void lateMinutes() {
        long lateMinutes = Duration.between(
                LocalTime.of(9, 0),
                LocalTime.now()).toMinutes();
        log.info("9点与当前时间隔了{}分钟", lateMinutes);
    }

    @Test
    public void zonedDateTimeDemo() {
        ZonedDateTime ldt = ZonedDateTime.now();
        Instant now = ldt.toInstant();
        log.info(" now {}", now);
    }

    public Instant toInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime());
    }

    public Date toDate(Instant instant) {
        return new Date(instant.toEpochMilli());
    }

    public Instant toBeijingInstant(LocalDateTime ldt) {
        return ldt.toInstant(ZoneOffset.of("+08:00"));
    }

    public Date toDate(LocalDateTime ldt) {
        return new Date(ldt.atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli());
    }

    public Calendar toCalendar(ZonedDateTime zdt) {
        TimeZone tz = TimeZone.getTimeZone(zdt.getZone());
        Calendar calendar = Calendar.getInstance(tz);
        calendar.setTimeInMillis(zdt.toInstant().toEpochMilli());
        return calendar;
    }

    public LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date.getTime()),
                ZoneId.systemDefault());
    }

    public ZonedDateTime toZonedDateTime(Calendar calendar) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(calendar.getTimeInMillis()),
                calendar.getTimeZone().toZoneId());
        return zdt;
    }

}