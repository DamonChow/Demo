package com.damon.redis.redisson.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/9/20 13:28
 */
public class Worker implements Runnable {

    protected static Logger logger = LoggerFactory.getLogger(Worker.class);

    private final CountDownLatch startSignal;

    private final CountDownLatch doneSignal;

    public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    @Override
    public void run() {
        /*try {
            startSignal.await();
            distributedLockTemplate.lock(new DistributedLockCallback<Object>() {

                @Override
                public Object process() {
                    doTask();
                    return null;
                }

                @Override
                public String getLockName() {
                    return "MyLock";
                }
            });
        } catch (InterruptedException ex) {
        } // return;  */
    }

    void doTask() {
        logger.info("{} start", Thread.currentThread().getName());
        Random random = new Random();
        int _int = random.nextInt(200);
        logger.info("{} sleep {} millis.", new Object[]{Thread.currentThread().getName(), _int});
        try {
            Thread.sleep(_int);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("{} end.", Thread.currentThread().getName());
        doneSignal.countDown();
    }

}
