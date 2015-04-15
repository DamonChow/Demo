package com.damon.test;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/4/15 11:34.
 */
public class CountDownLatchTest {

    @Test
    public void test123423() throws Exception {
        final int N=5;
        final CountDownLatch startSignal = new CountDownLatch(1);
        final CountDownLatch doneSignal = new CountDownLatch(N);
        final Random r=new Random();
        for (int i = 0; i < N; ++i)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startSignal.await();
                        System.out.println(Thread.currentThread().getName()+" starts!");
                        Thread.sleep(r.nextInt(5000));
                        System.out.println(Thread.currentThread().getName()+" done!");
                    } catch (Exception e) {}
                    doneSignal.countDown();
                }
            }).start();
        System.out.println("All workers start!");
        startSignal.countDown();
        doneSignal.await();
        System.out.println("All workers done!");
    }
}
