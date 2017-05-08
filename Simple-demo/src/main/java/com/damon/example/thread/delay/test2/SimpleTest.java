package com.damon.example.thread.delay.test2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2017/1/4 10:24
 */
public class SimpleTest {

    private static Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    public static void main(String[] args) {
        int activitySize = 10;
        ExecutorService pool = Executors.newFixedThreadPool(10);
        logger.info("开始.");
        Date now = new Date();
        Random r = new Random();
        for (int i = 1; i <= activitySize; i++) {
            pool.submit(new ActivityHandler(new ActivityMessage(i, "活动"+i, 1, now, r.nextInt(2000)+1000)));
            pool.submit(new ActivityHandler(new ActivityMessage(i, "活动"+i, 2, now, r.nextInt(5000)+2000)));
        }
        try {
            pool.awaitTermination(10l, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("结束.");
    }
}

class ActivityHandler<Integer> implements Delayed, Callable {

    private Logger logger = LoggerFactory.getLogger(ActivityHandler.class);

    private ActivityMessage message;

    public ActivityMessage getMessage() {
        return message;
    }

    public void setMessage(ActivityMessage message) {
        this.message = message;
    }

    public ActivityHandler(ActivityMessage message) {
        this.message = message;
    }

    @Override
    public Integer call() throws Exception {
        switch (message.getType()) {
            case 1:
                logger.info("活动名称为[{}]且活动id为[{}]正式开始,延迟了多久[{}]",
                        new Object[]{message.getName(), message.getActivityId(), message.getDelay()});
                break;
            case 2:
                logger.info("活动名称为[{}]且活动id为[{}]结束,延迟了多久[{}]",
                        new Object[]{message.getName(), message.getActivityId(), message.getDelay()});
                break;
            default:
                logger.info("nothing , wrong type..");
        }
        return null;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return message.getDelay(unit);
    }

    @Override
    public int compareTo(Delayed o) {
        return message.compareTo(((ActivityHandler)o).getMessage());
    }
}

class ActivityMessage {

    private int activityId;

    private String name;

    private int type;

    private Date time;

    private long delay;

    public ActivityMessage(int activityId, String name, int type, Date time, long delay) {
        this.activityId = activityId;
        this.name = name;
        this.type = type;
        this.time = time;
        this.delay = delay;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(delay - System.nanoTime(), unit.NANOSECONDS);
    }

    public int compareTo(ActivityMessage that) {
        return delay > that.delay ? 1 : (delay < that.delay ? -1 : 0);
    }

    public String toString() {
        return "Message{" +
                "activityId=" + activityId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", time=" + time +
                ", delay=" + delay +
                '}';
    }
}